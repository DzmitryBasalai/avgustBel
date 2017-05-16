package spring.service.infoTable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import java.util.Random;


public class InfoTableAdapterTCP implements InfoTableAdapter {

    private String ipAddr;
    private int port;
    private int InfoTableAddress;
    private Socket socket;

    public InfoTableAdapterTCP() {

    }

    public InfoTableAdapterTCP(String IP, int port, int address) {
        ipAddr = IP;
        this.port = port;
        InfoTableAddress = address;
    }

    @Override
    public void SendData(List<String> data) throws Exception {
        try {

            for (int i = 0; i < data.size(); i++) {
                InetAddress ipAddress = InetAddress.getByName(this.ipAddr);
                this.socket = new Socket(ipAddress, this.port, false);
                this.socket.setSoTimeout(500);

                InputStream sin = socket.getInputStream();
                OutputStream sout = socket.getOutputStream();

                byte[] displayRequest = makeDisplayRequest(i + 1, data.get(i));
                byte[] L2_package = makeL2pack(displayRequest, InfoTableAddress, (byte) 0x05);
                byte[] CRC = countCS(L2_package, L2_package.length);
                int length = L2_package.length;

                byte[] encryptedL2_package = encryptL2pack(L2_package);
                byte[] dataToSend = makeL1pack(encryptedL2_package, length, CRC);

                sout.write(dataToSend);
                sout.flush();

                byte[] result = new byte[32];
                int count = sin.read(result);
                if (count == -1) {
                    int newCount = 0;
                    int tryToResendCount = 0;
                    do {
                        this.socket.close();
                        ipAddress = InetAddress.getByName(this.ipAddr);
                        this.socket = new Socket(ipAddress, this.port, false);

                        sin = socket.getInputStream();
                        sout = socket.getOutputStream();
                        sout.write(dataToSend);
                        sout.flush();
                        newCount = sin.read(result);
                    } while (newCount == -1 && (tryToResendCount++) < 4);
                }

                boolean lengthInd = false;
                int respLength = 0;

                while (!lengthInd) {
                    if (result[respLength++] == 0x03) {
                        lengthInd = true;
                    }
                }

                byte[] resultToDecrypt = new byte[respLength - 6];

                for (int j = 0, k = 3; j < resultToDecrypt.length; j++, k++) {
                    resultToDecrypt[j] = result[k];
                }
                byte[] decr = decryptL2pack(resultToDecrypt);
                System.out.println("Status: " + decr[7]);
                this.socket.close();
            }
        } catch (UnknownHostException ex) {
            throw new Exception("Unable to find remote host (" + this.ipAddr + ":" + this.port + ").\n" + ex.getMessage());
        } catch (SocketException ex) {
            throw new Exception("Unable to create remote host connection (" + this.ipAddr + ":" + this.port + ").\n" + ex.getMessage());
        } catch (IOException ex) {
            throw new Exception("Unable to connect remote host (" + this.ipAddr + ":" + this.port + ").\n" + ex.getMessage());
        }
    }

    @Override
    public void SetBrightness(int brightness) throws Exception {
        try {
            InetAddress ipAddress = InetAddress.getByName(this.ipAddr);
            this.socket = new Socket(ipAddress, this.port, false);

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();


            byte[] displayRequest = makeBrightnessRequest(brightness);
            byte[] L2_package = makeL2pack(displayRequest, 9999, (byte) 0x02);
            byte[] CRC = countCS(L2_package, L2_package.length);
            int length = L2_package.length;

            byte[] encryptedL2_package = encryptL2pack(L2_package);
            byte[] dataToSend = makeL1pack(encryptedL2_package, length, CRC);

            sout.write(dataToSend);
            sout.flush();

            byte[] result = new byte[32];
            int count = sin.read(result);
            if (count == -1) {
                int newCount = 0;
                int tryToResend = 0;
                do {
                    this.socket.close();
                    ipAddress = InetAddress.getByName(this.ipAddr);
                    this.socket = new Socket(ipAddress, this.port, false);

                    sin = socket.getInputStream();
                    sout = socket.getOutputStream();
                    sout.write(dataToSend);
                    sout.flush();
                    newCount = sin.read(result);
                } while (newCount == -1 && (tryToResend++) < 5);
            }
            this.socket.close();
        } catch (UnknownHostException ex) {
            throw new Exception("Unable to find remote host (" + this.ipAddr + ":" + this.port + ").\n" + ex.getMessage());
        } catch (SocketException ex) {
            throw new Exception("Unable to create remote host connection (" + this.ipAddr + ":" + this.port + ").\n" + ex.getMessage());
        } catch (IOException ex) {
            throw new Exception("Unable to connect remote host (" + this.ipAddr + ":" + this.port + ").\n" + ex.getMessage());
        }
    }

    //Создание запроса на вывод текста
    private byte[] makeDisplayRequest(int displayNumber, String text) {
        byte[] request = new byte[text.length() + 9];

        try {
            byte[] textAsByteArray = text.getBytes("windows-1251");
            request[0] = (byte) displayNumber;
            request[1] = 0x04;
            request[2] = 0x02;
            request[3] = (byte) 0x80;
            request[4] = 0x03;
            request[5] = 0x01;
            request[6] = 0x00;
            request[7] = 0x01;
            request[8] = 0x19;
            for (int i = 0, j = 9; i < textAsByteArray.length; i++) {
                request[j] = textAsByteArray[i];
                j++;
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return request;
    }

    //Создание запроса на вывод текста
    private byte[] makeBrightnessRequest(int value) {
        byte[] request = new byte[1];
        request[0] = (byte) (value & 0x00FF);

        return request;
    }

    //Создание незашифрованного пакета 2го уровня
    private static byte[] makeL2pack(byte[] request, int address, byte fNum) {
        byte[] L2_package = new byte[request.length + 10];
        Random random = new Random();
        L2_package[0] = 0x00;
        L2_package[1] = 0x00;
        L2_package[2] = (byte) (address & 0x00FF);
        L2_package[3] = (byte) ((address >> 8) & 0x00FF);
        L2_package[4] = (byte) (random.nextInt() & 0x007F);
        L2_package[5] = fNum;
        L2_package[6] = 0x00;
        L2_package[7] = (byte) 0x80;
        L2_package[8] = (byte) (request.length & 0x00FF);
        L2_package[9] = (byte) ((request.length >> 8) & 0x00FF);

        for (int i = 0, j = 10; i < request.length; i++) {
            L2_package[j] = request[i];
            j++;
        }

        return L2_package;
    }

    //Шифрование пакета 2го уровня
    private static byte[] encryptL2pack(byte[] message) {
        byte[] encryptedData = new byte[message.length * 2];
        int index = 0, indexEnc = 0;
        short temp;

        while (index < message.length) {
            temp = (short) (message[index] & 0x00FF);
            temp ^= 0x0080;

            if (temp < 0x20 || temp == 0x7F) {
                encryptedData[indexEnc] = 0x7F;
                encryptedData[indexEnc + 1] = (byte) ((temp | 0x0080) & 0x00FF);
                indexEnc += 2;
            }
            else {
                encryptedData[indexEnc] = (byte) (temp & 0x00FF);
                indexEnc++;
            }

            index++;
        }

        byte[] result = new byte[indexEnc];
        for (int i = 0; i < indexEnc; i++) {
            result[i] = encryptedData[i];
        }
        return result;
    }

    //Шифрование пакета 2го уровня
    private static byte[] decryptL2pack(byte[] message) {
        byte[] decryptedData = new byte[message.length];
        int index = 0, indexDec = 0;
        short temp;

        while (index < message.length) {
            temp = (short) (message[index] & 0x00FF);

            if (temp == 0x7F) {
                index++;
                temp = (short) (message[index] & 0x00FF);
                decryptedData[indexDec] = (byte) ((temp ^ 0x0080) & 0x00FF);
            }
            else {
                decryptedData[indexDec] = (byte) ((temp ^ 0x0080) & 0x00FF);
            }
            indexDec++;
            index++;
        }

        byte[] result = new byte[indexDec];
        for (int i = 0; i < indexDec; i++) {
            result[i] = decryptedData[i];
        }
        return result;
    }

    //Создание пакета первого уровня
    private static byte[] makeL1pack(byte[] data, int L2Length, byte[] crc) {
        byte[] result = new byte[data.length + 6];

        result[0] = 0x02;
        result[1] = (byte) (((L2Length & 0x00FF) & 0x007F) | 0x0080);
        result[2] = (byte) ((((L2Length >> 8) & 0x00FF) & 0x007F) | 0x0080);
        for (int i = 0, j = 3; i < data.length; i++) {
            result[j] = data[i];
            j++;
        }
        result[result.length - 3] = crc[0];
        result[result.length - 2] = crc[1];
        result[result.length - 1] = 0x03;

        return result;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getInfoTableAddress() {
        return InfoTableAddress;
    }

    public void setInfoTableAddress(int InfoTableAddress) {
        this.InfoTableAddress = InfoTableAddress;
    }

    //Расчет контрольной суммы
    private static byte[] countCS(byte[] Data, int Cnt) {
        byte cs1;
        short cs2, tempCRC;
        byte[] result = new byte[2];
        int i = 0;
        cs1 = 0;
        cs2 = 0;
        while (i < Cnt) {
            cs1 = CRC_TABLE[(short) ((cs1 ^ (Data[i])) & 0x00FF)];
            cs2 += ~(Data[i]) & 0x00FF;
            i++;
        }
        tempCRC = (short) (0x8080 | ((short) cs1 & 0x00FF) | (((((short) cs1 & 0x00FF)) << 1) & 0x100) | ((((short) cs2) & 0xFF) << 9));
        result[0] = (byte) (tempCRC & 0x00FF);
        result[1] = (byte) ((tempCRC & 0xFF00) >> 8);

        return result;
    }

    //Константы для расчета контрольной суммы
    static final byte[] CRC_TABLE = {
            0, 94, (byte) 188, (byte) 226, 97, 63, (byte) 221, (byte) 131, (byte) 194, (byte) 156, (byte) 126, 32, (byte) 163, (byte) 253, 31, 65,
            (byte) 157, (byte) 195, 33, 127, (byte) 252, (byte) 162, 64, 30, 95, 1, (byte) 227, (byte) 189, 62, 96, (byte) 130, (byte) 220,
            35, (byte) 125, (byte) 159, (byte) 193, 66, 28, (byte) 254, (byte) 160, (byte) 225, (byte) 191, 93, 3, (byte) 128, (byte) 222, 60, 98,
            (byte) 190, (byte) 224, 2, 92, (byte) 223, (byte) 129, 99, 61, 124, 34, (byte) 192, (byte) 158, 29, 67, (byte) 161, (byte) 255,
            70, 24, (byte) 250, (byte) 164, 39, 121, (byte) 155, (byte) 197, (byte) 132, (byte) 218, 56, 102, (byte) 229, (byte) 187, 89, 7,
            (byte) 219, (byte) 133, 103, 57, (byte) 186, (byte) 228, 6, 88, 25, 71, (byte) 165, (byte) 251, 120, 38, (byte) 196, (byte) 154,
            101, 59, (byte) 217, (byte) 135, 4, 90, (byte) 184, (byte) 230, (byte) 167, (byte) 249, 27, 69, (byte) 198, (byte) 152, 122, 36,
            (byte) 248, (byte) 166, 68, 26, (byte) 153, (byte) 199, 37, 123, 58, 100, (byte) 134, (byte) 216, 91, 5, (byte) 231, (byte) 185,
            (byte) 140, (byte) 210, 48, 110, (byte) 237, (byte) 179, 81, 15, 78, 16, (byte) 242, (byte) 172, 47, 113, (byte) 147, (byte) 205,
            17, 79, (byte) 173, (byte) 243, 112, 46, (byte) 204, (byte) 146, (byte) 211, (byte) 141, 111, 49, (byte) 178, (byte) 236, 14, 80,
            (byte) 175, (byte) 241, 19, 77, (byte) 206, (byte) 144, 114, 44, (byte) 109, 51, (byte) 209, (byte) 143, 12, 82, (byte) 176, (byte) 238,
            50, (byte) 108, (byte) 142, (byte) 208, 83, 13, (byte) 239, (byte) 177, (byte) 240, (byte) 174, 76, 18, (byte) 145, (byte) 207, 45, 115,
            (byte) 202, (byte) 148, 118, 40, (byte) 171, (byte) 245, 23, 73, 8, 86, (byte) 180, (byte) 234, 105, 55, (byte) 213, (byte) 139,
            87, 9, (byte) 235, (byte) 181, 54, 104, (byte) 138, (byte) 212, (byte) 149, (byte) 203, 41, 119, (byte) 244, (byte) 170, 72, 22,
            (byte) 233, (byte) 183, 85, 11, (byte) 136, (byte) 214, 52, 106, 43, 117, (byte) 151, (byte) 201, 74, 20, (byte) 246, (byte) 168,
            116, 42, (byte) 200, (byte) 150, 21, 75, (byte) 169, (byte) 247, (byte) 182, (byte) 232, 10, 84, (byte) 215, (byte) 137, 107, 53};
}
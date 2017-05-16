package spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import spring.DAO.ClientDao;
import spring.model.Client;
import spring.model.Order;
import spring.view.ExcelReportView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Basalai on 4/26/2017.
 */
@Service
public class ExcelService {

    @Autowired
    ClientDao clientDao;


    public ModelAndView uploadFile(HttpServletRequest request){
        String from = request.getParameter("fromInput");
        String to = request.getParameter("toInput");
        ModelAndView model = new ModelAndView(new ExcelReportView());
        model.addObject("clientServedList",clientDao.getClientListByState(from, to ,4));
        model.addObject("clientReturnedList",clientDao.getClientListByState(from, to ,5));
        return model;
    }

    public ModelAndView downloadExcelFile(HttpServletRequest request){
        Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
        Map<String,Object> mapOrder;
        List<Order> orderList;
        String messageDownloadFile;
        int state;
        if(map!=null){
            mapOrder = (HashMap<String,Object>)map.get("mapOrder");
            orderList = (List<Order>)mapOrder.get("orderList");
            messageDownloadFile = (String)mapOrder.get("message");
            state = (Integer) mapOrder.get("state");
        }else{
            orderList = new ArrayList<Order>();
            messageDownloadFile= "";
            state=0;
        }

        ModelAndView modelAndView = new ModelAndView("operator/dataOperations");
        modelAndView.addObject("orderList",orderList);
        modelAndView.addObject("fileDownloadMes",messageDownloadFile);
        modelAndView.addObject("state",state);

        return modelAndView;
    }
}

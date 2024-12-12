package com.example.controller;

import com.example.model.StateInfoModel;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.List;
import java.util.ArrayList;

@WebServlet(name = "StateInfoServlet", urlPatterns = {"/getStateInfo"})
public class StateInfoServlet extends HttpServlet {

    private StateInfoModel stateInfoModel;

    @Override
    public void init() {
        stateInfoModel = new StateInfoModel();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String stateName = request.getParameter("stateName");

        String statePopulation = "";
        String stateImageURL = "";
        // Other state information
        String stateData = stateInfoModel.getStateData();
        statePopulation = stateInfoModel.getStatePopulation(stateName, stateData);
        stateImageURL = stateInfoModel.getStateImageURL(stateName, "state flower");
        // Fetch other state information

        request.setAttribute("statePopulation", statePopulation);
        request.setAttribute("stateImageURL", stateImageURL);
        // Set other attributes for state information

        RequestDispatcher view = request.getRequestDispatcher("state-info.jsp");
        view.forward(request, response);
    }
}

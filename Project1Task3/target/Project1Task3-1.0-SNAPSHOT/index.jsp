<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Select State</title>
</head>
<body>
<form action="getStateInfo" method="GET">
  <label for="stateName">Select a State:</label>
  <select name="stateName" id="stateName">
    <!-- Populate dropdown with state names -->
    <%
      String response = (String) request.getAttribute("stateData");
      if (response != null) {
        com.google.gson.JsonArray jsonArray = new com.google.gson.JsonParser().parse(response).getAsJsonArray();
        for (com.google.gson.JsonElement element : jsonArray) {
          String name = element.getAsJsonArray().get(0).getAsString();
          out.println("<option value=\"" + name + "\">" + name + "</option>");
        }
      }
    %>
  </select>
  <br>
  <!-- Add other inputs (radio buttons, text fields, etc.) as needed -->
  <input type="submit" value="Get State Info">
</form>
</body>
</html>

<%-- 
    Document   : createMessage
    Created on : Apr 5, 2015, 8:09:40 PM
    Author     : yanma
--%>

<%@page import="models.user.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/admin_style.css" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Send Message</title>
    </head>
    <body>
        <% User receiver = (User)request.getAttribute("receiver"); 
           String receiverName = receiver.getUserName();
        %>
        <% if (receiver != null) { %>
            <div class="div_system_title">
                <h1><b>Create new message</b></h1>
            </div>
            <fieldset id="div_current_user_fieldset">
                <legend id="div_current_user_legend">Please enter the message content</legend>
            </fieldset>
            <div id="div_current_user">
                <form action="MessageCreate" method="post" class="form-horizontal" >
                    <div class="form-group">
                        <label class="col-sm-2 control-label">User Name</label>
                        <div class="col-sm-10">
                            <input type="text" name="receiver" value="<%=receiver.getUserName()%>" class="form-control"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Message Content</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" rows="3" name="messageContent"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <input type="submit" name="createMessage" value="Send Message" class="btn btn-default"/>
                            <input type="reset" value="Clear" class="btn btn-default"/>
                            <input type="submit" name="Back" value="Back" class="btn btn-default"/>
                        </div>
                    </div>
                </form>
            </div>
        <%}
        else { %>
            <div class="div_system_title">
                <h1><b>There is no valid user to send message!</b></h1>
            </div>
        <% }
           
           
           
        %>
        
           
        <h1>Hello World!</h1>
    </body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="page" value="${param['page']}" />

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">MetaDock</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">

                <c:choose>
                    <c:when test="${page=='index'}">
                        <li class="active"><a href="#">Home <span class="sr-only">(current)</span></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href='<c:url value="/." />'>Home</a></li>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${page=='tutorial'}">
                        <li class="active"><a href="#">Tutorial <span class="sr-only">(current)</span></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href='<c:url value="/tutorial.jsp" />'>Tutorial</a></li>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${page=='benchmark'}">
                        <li class="active"><a href="#">Benchmark <span class="sr-only">(current)</span></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href='<c:url value="/benchmark.jsp" />'>Benchmark</a></li>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${page=='task'}">
                        <li class="active"><a href="#">Task <span class="sr-only">(current)</span></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href='<c:url value="/task.jsp" />'>Task</a></li>
                    </c:otherwise>
                </c:choose>
                
                 <c:choose>
                    <c:when test="${page=='references'}">
                        <li class="active"><a href="#">References <span class="sr-only">(current)</span></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href='<c:url value="/references.jsp" />'>References</a></li>
                    </c:otherwise>
                </c:choose>
                

                <c:choose>
                    <c:when test="${page=='apiDocumentation'}">
                        <li class="active"><a href="#">API documentation <span class="sr-only">(current)</span></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href='<c:url value="/apiDocumentation.jsp" />'>API documentation</a></li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div>
    </div>
</nav>

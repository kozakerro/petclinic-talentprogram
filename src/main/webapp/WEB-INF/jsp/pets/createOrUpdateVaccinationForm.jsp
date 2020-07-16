<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="owners">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#vaccinationDate").datepicker({dateFormat: 'yy/mm/dd'});
                $("#expirationDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${vaccination['new']}">New </c:if>Vaccination</h2>

        <b>Pet</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Birth Date</th>
                <th>Type</th>
                <th>Owner</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${vaccination.pet.name}"/></td>
                <td><petclinic:localDate date="${vaccination.pet.birthDate}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${vaccination.pet.type.name}"/></td>
                <td><c:out value="${vaccination.pet.owner.firstName} ${vaccination.pet.owner.lastName}"/></td>
            </tr>
        </table>

        <form:form modelAttribute="vaccination" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Vaccination date" name="vaccinationDate"/>
                <petclinic:inputField label="Expiration date" name="expirationDate"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${vaccination.pet.id}"/>
                    <button class="btn btn-default" type="submit">Add Vaccination</button>
                </div>
            </div>
        </form:form>

        <br/>
        <b>Previous Vaccinations</b>
        <table class="table table-striped">
            <tr>
                <th>Date</th>
                <th>Description</th>
            </tr>
            <c:forEach var="vaccination" items="${vaccination.pet.vaccinations}">
                <c:if test="${!vaccination['new']}">
                    <tr>
                        <td><petclinic:localDate date="${vaccination.vaccinationDate}" pattern="yyyy/MM/dd"/></td>
                        <td><petclinic:localDate date="${vaccination.expirationDate}" pattern="yyyy/MM/dd"/></td>
                        <td><c:out value="${vaccination.name}"/></td>
                        <td><c:out value="${vaccination.vet}"/></td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </jsp:body>

</petclinic:layout>

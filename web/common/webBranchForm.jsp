<%@ include file="/web/common/includes/taglibs.jsp"%>
<jsp:include page="/web/common/includes/header.jsp" flush="true" />
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false">/*&callback=initialize*/</script>
<script>

var lat;
var longi;
var loc;
var locat;

function getLocation() {
	//alert("getting location");
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition);
  } else { 
//    alert("Geolocation is not supported by this browser.");
  }
  
}



async function initializeMap(latVar,lngVar) {
	  // Request needed libraries.
	 // alert("initializing map");
	  const { Map, InfoWindow } = await google.maps.importLibrary("maps");
	  const { AdvancedMarkerElement,PinElement} = await google.maps.importLibrary("marker");
	 //alert("latVar "+latVar+",lngVar "+lngVar);
	  const map = new Map(document.getElementById("latlongmape"), {
	    center: { lat: latVar, lng: lngVar },
	    zoom: 20,
	   // mapId: "4504f8b37365c3d0",
	  });
	// alert(map);
	 
	// Adjust the scale.
	 const pinScaled = new PinElement({
	   scale: 15,
	   background: "#FBBC04",
	   borderColor: "#137333"
	 });
	
	 const markerView = new AdvancedMarkerElement({
		    map,
		    position: { lat: latVar, lng: lngVar },
		    content: pinScaled.element,
		  });
	 	 
	  const infoWindow = new InfoWindow();
	  const draggableMarker = new AdvancedMarkerElement({
	    map,
	    position: { lat: latVar, lng: lngVar },
	    gmpDraggable: true,
	    title: "This marker is draggable.",
	  });
	 // alert(draggableMarker.title);
	  draggableMarker.addListener("dragend", (event) => {
	    const position = draggableMarker.position;

	    infoWindow.close();
	    infoWindow.setContent(
	      'Pin dropped at: ${position.lat()}, ${position.lng()}'
	    );
	    infoWindow.open(draggableMarker.map, draggableMarker);
	  });
	  //alert("4");
}

	
	
function showPosition(position) {
	//longi=position.coords.longitude;
	  //lat=position.coords.latitude;
	  
	locat = document.getElementById("locat");
  locat.value = position.coords.latitude + "," + position.coords.longitude;
  
 // loc=position.coords.latitude + "/" + position.coords.longitude;
  
 // alert(locat.value);
  
// var longit = document.getElementById(long);
 //var latit = document.getElementById(lat);
// longit.value=longi;
// latit.value=lat;
  
  var latlongmape = document.getElementById("latlongmape");
  latlongmape.src = latlongmape.src+locat.value;
 //alert(latlongmape.src);
 
 
  // marker drag event
 /* latlongmape.event.addListener(marker,'drag',function(event) {
	  alert(event);
      document.getElementById('lat').value = event.latLng.lat();
      document.getElementById('lng').value = event.latLng.lng();
  });*/

  //marker drag event end
 /*latlongmape.event.addListener(marker,'dragend',function(event) {
      document.getElementById('lat').value = event.latLng.lat();
      document.getElementById('lng').value = event.latLng.lng();
  });*/
  
//marker click event
//alert("will place marker");
  google.maps.event.addListener(latlongmape, 'click', function(e) {
	    placeMarker(e.latLng, latlongmape);
	  });
  
	  initializeMap(position.coords.latitude,position.coords.longitude);
}

function placeMarker(position, map) {
	  var marker = new google.maps.Marker({
	    position: position,
	    map: map,
	    scale: 15,
		   background: "#FBBC04",
		   borderColor: "#137333"
	  });
	  map.panTo(position);
	}
window.onload=getLocation();
</script>

<table width="90%" border="0" cellspacing="0" cellpadding="0"
	style="padding-right:10px ">
	<tr>
		<td class="tableHeader" height="1"></td>
	</tr>
	<tr>
		<td class="tableHeader"><abc:i18n
			property="commons.caption.branchForm" /> <fmt:message
			key="commons.caption.branchForm" /></td>
		<td align="left"></td>
	</tr>
	<tr>
		<td colspan="2" bgcolor="#5A718B" height="2"></td>
	</tr>
	<tr>
		<td colspan="2" height="1"></td>
	</tr>

	<tr>
		<td colspan="2" height="20"></td>
	</tr>

	<tr>
		<td colspan="2">

		<form  method="POST"
			action="<c:url value="/common/webBranchForm.html"/>"><spring:bind
			path="branch.*">
			<c:if test="${not empty status.errorMessages}">
				<div><c:forEach var="error" items="${status.errorMessages}">
					<font color="red"> <c:out value="${error}" escapeXml="false" /><br />
					</font>
				</c:forEach></div>
			</c:if>
			
			<input type="hidden" size=20 maxlength=40 id="locat" name="locat"/>
			
		</spring:bind> <spring:bind path="branch.id">
			<input type="hidden" size=20 maxlength=40 name="branchId"
				value="${status.value}" />
		</spring:bind> <input type="hidden" size=20 maxlength=40
			name="companyId" value="${companyId}" />

		<table border=0 cellspacing=1 cellpadding=0 id="ep"
			style="margin-right:40px">

			<tr>
				<td colspan=5>&nbsp;</td>
			</tr>
			<tr id="head_1_ep">
				<td class="bodyBold" colspan=4 nowrap><abc:i18n
					property="commons.caption.branchForm" /> <fmt:message
					key="commons.caption.branchForm" /></td>
				<td nowrap colspan=1 align=left><img
					src="<c:url value="/web/common/images/required_icon.gif"/>"
					border="0" alt="Required Information" title="Required Ination"
					width=18 height=18 align="texttop"><span class="bodySmallBold"> = <abc:i18n
					property="commons.caption.requiredInformation" /><fmt:message
					key="commons.caption.requiredInformation" /></span></td>
			</tr>
			<TR>
				<TD CLASS="blackLine" COLSPAN=5><img
					src="<c:url value="/web/common/images/s.gif"/>"></TD>
			</TR>

					<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.caption.description" /> <fmt:message
								key="commons.caption.description" /></td>
						<td><spring:bind path="branch.descr">
								<input type="text" size="40" name="${status.expression}"
									value="${status.value}"></input>
							</spring:bind></td>
					</tr>

					<tr>
					
						<tr>
						<td nowrap class="requiredInput"><abc:i18n
								property="commons.caption.address" /> <fmt:message
								key="commons.caption.address" /></td>
								
								
						<td class="formBodControl" colspan="7">
						<div id="mapDiv" class="map">
						<iframe 
								id="latlongmape" width="450" height="250"
								style="border: 0;" class="shadow"
								src='https://www.google.com/maps/embed/v1/place?key=AIzaSyBeCCPQ7VdCQiJxjXGfVO98LyirL1-hC74&maptype=roadmap&q=${locat}'> 
						</iframe></div></td>
					</tr>

					<tr>
					
					<tr>
						<td colspan=5>&nbsp;</td>
					</tr>


					<tr id="btn">
				<td colspan=5 align=center><abc:i18n property="commons.button.save" />
				<input type="submit" name="save"
					value="<fmt:message key="commons.button.save"/>" class="button" />&nbsp;&nbsp;&nbsp;

				<abc:i18n property="commons.button.cancel" /> <input type="reset"
					name="cancel" value="<fmt:message key="commons.button.cancel"/>"
					class="button" /></td>
			</tr>
			
		</td>
	</tr>
</table>
</form>
</td>
</tr>
</table>



<%@ include file="/web/common/includes/footer.jsp"%>

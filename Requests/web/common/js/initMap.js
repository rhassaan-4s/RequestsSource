//function initMap() {
//	var longitude = $("#lng").val();
//    var latitude  = $("#lat").val();   
//	var mapProp= {
//	  center:new google.maps.LatLng(51.508742,-0.120850),
//	  zoom:8,
//	};
//	var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
//}

//$(document).ready(
		
$(document).ready(function() {
    $(window).resize(function() {
        google.maps.event.trigger(map, 'resize');
    });
    google.maps.event.trigger(map, 'resize');
});

function initMap(){
	 $(window).resize(function() {
	        google.maps.event.trigger(map, 'resize');
	    });
		var longitude = $("#lng").val();
		var latitude  = $("#lat").val();   
		//alert("longitude " + longitude + " latitude " + latitude );
		
//		var mapProp= {
//				center:new google.maps.LatLng(latitude,longitude),
//				zoom:8,
//				mapTypeId: google.maps.MapTypeId.ROADMAP,
//		};
//		alert("map prop " + mapProp.center);
//		var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
		
		 var mapCanvas = document.getElementById('map-canvas');
         var mapOptions = {
             zoom: 8,
             center: new google.maps.LatLng(latitude, longitude),
             mapTypeId: google.maps.MapTypeId.ROADMAP,
         }
         var map = new google.maps.Map(mapCanvas, mapOptions)
         $(window).resize(function() {
             google.maps.event.trigger(map, 'resize');
         });
}

//$(document).ready(function(){
//	initMap();
//});


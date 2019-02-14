(function($){
	  CalendarChecktype = function() {
		  var text = $(":input#dateType").val();
		  switch(text){
		  case '0':
			 $('.calendar').calendarsPicker();
			 $('input[class="CheckOut"]').calendarsPicker();
			 break;
		  case '1':
			  $('.calendar').calendarsPicker({calendar: $.calendars.instance('islamic')});
			  $('input[class="CheckOut"]').calendarsPicker({calendar: $.calendars.instance('islamic')});
			  break;
		  }
		  };
})(jQuery);

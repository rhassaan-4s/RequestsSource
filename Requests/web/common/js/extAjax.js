

	 function fillShippingDataDep(data)
	     {
	         
	          if(data != null){
	        	  storesManager.getExtDestByAjax(fillExtAgent,data.destcode,'agent');
	        	  storesManager.getExtDestByAjax(fillExtDriver,data.drivercode,'driver');
	        	  storesManager.getExtDestByAjax(fillExtCarCode,data.carcode,'cars');
	        	  storesManager.getExtDestByAjax(fillExtCarType,data.cartypecode,'car_type');
	        	  storesManager.getExtDestByAjax(fillExtFromArea,data.fromcode,'area');
	        	  storesManager.getExtDestByAjax(fillExtToArea,data.tocode,'area');
		          
		          if(data.driver_name!= null){
	        	  	document.getElementById("shippingdrivername").value= data.driver_name;
		          }
		          if(data.accommodation!=null){
	        	  	document.getElementById("shippingaccommodation").value= data.accommodation;
		          }
		          if(data.telephon_number!=null){
		        	document.getElementById("shippingtelephonnumber").value= data.telephon_number;
		          }
	        	  if(data.car_number!=null){
	        	  	document.getElementById("shippingcarnumber").value= data.car_number;
	        	  }
	        	  if(data.carown!=null){
	        	  	document.getElementById("shippingcarown").value= data.carown;
	        	  }
	        	  if(data.rent_amount!=null){
	        	  	document.getElementById("shippingrentamount").value= data.rent_amount;
	        	  }
	        	   
	          }
	     }


		 function fillExtAgent(data){
			 if(data!= null && data.length>0){
				 dest = data[0];
				    document.getElementById("destCodeDesc").value = dest.typedesc;
					document.getElementById("destCodeDeschidden").value = dest.typeid;
					var paramString1="driver,"+dest.typeid;
					var paramString2="cars,"+dest.typeid;				
					changeHiddenValues('driverCodeDesc', 'driver','commons.caption.description', '', 'name',
							'type_code', paramString1, '');
					changeHiddenValues('carCodeDesc', 'cars','commons.caption.code', '', 'type_code',
							'type_code', paramString2, '');
			 }
		 }

		 function fillExtDriver(data){
			
			 if(data!= null && data.length>0){
				 dest = data[0];
					document.getElementById("driverCodeDesc").value = dest.typedesc;
					document.getElementById("driverCodeDeschidden").value = dest.typeid;
			 }
		 }

		 function fillExtCarCode(data){
			 
			 if(data!= null && data.length>0){
				 dest = data[0];
					document.getElementById("carCodeDesc").value = dest.typeid;
					document.getElementById("carCodeDeschidden").value = dest.typeid; 
			 }
		 }

		 function fillExtCarType(data){
			 if(data!= null && data.length>0){
				 dest = data[0];
					document.getElementById("carTypeDesc").value = dest.typedesc;
					document.getElementById("carTypeDeschidden").value = dest.typeid;
			 }
		 }

		 function fillExtFromArea(data){
			 if(data!= null && data.length>0){
				 dest = data[0];
					document.getElementById("fromCodeDesc").value = dest.typedesc;
					document.getElementById("fromCodeDeschidden").value = dest.typeid; 
			 }
		 }

		 function fillExtToArea(data){
			 if(data!= null && data.length>0){
				 dest = data[0];
					document.getElementById("toCodeDesc").value = dest.typedesc;
					document.getElementById("toCodeDeschidden").value = dest.typeid;
			 }
		 }
	     
		 function fillExtDestFrom(data) {
				
				if (data != null) {
					for (j = 0; j < data.length; j++) {
						dest = data[j];
						document.getElementById("depFromDesc").value = dest.typedesc;
						document.getElementById("depFromId").value = dest.typeid;
					}
				}
			}

		 function fillExtDestTo(data) {
				
				if (data != null) {
					for (j = 0; j < data.length; j++) {
						dest = data[j];
						document.getElementById("depToDesc").value = dest.typedesc;
						document.getElementById("depToId").value = dest.typeid;
					}
				}
			}

		 function geUploadingOrScreeningPriceByEdit(row){
			 
				var price=0;
				var unitContractorPrice = document.getElementById("storeTransactionO["+row+"].unit_contractor_price").value
				var qty1 = document.getElementById("storeTransactionO["+row+"].qty1").value
			
				
				
				if(uploadAmountItem =='1'){
					if(qty1 != null){
					price = parseFloat(unitContractorPrice) * parseFloat(qty1);
					price = new Number(price+'').toFixed(parseInt(3));
					document.getElementById("storeTransactionO["+row+"].uploading_price").value = price;
					}
					
				}else if(screenAmountItem =='1' && qty1 !=""){
					if(qty1 != null){
					price = parseFloat(unitContractorPrice) * parseFloat(qty1);
					price = new Number(price+'').toFixed(parseInt(3));
					document.getElementById("storeTransactionO["+row+"].screening_price").value = price;
					}
				}

			}
		

			function getAllRoombBySelected (x,y,id){
				
				var tempDestination = document.getElementById("tempDestination");
				var destination = document.getElementById("storeTransactionO["+x+"].details["+y+"].destination");
				DWRUtil.removeAllOptions(destination);
		
				if(destination != null && tempDestination != null){
					for(var i = 0; i < tempDestination.options.length; i++){
						destination.options.add(new Option(tempDestination.options[i].text,tempDestination.options[i].value),i+1);
						if(destination.options[i].value==id){
							destination.selectedIndex = i;
							//destination.options[i].selected = true;
						}
					}
				}
			
			}
			
			function geUploadingOrScreeningPrice(row){
				
				var price=0;
				var unitContractorPrice;
				var unitContractorPriceObj = document.getElementById("storeTransactionO["+row+"].unit_contractor_price");
				if (unitContractorPriceObj ==null){
					unitContractorPrice = 0;
				}else{
					unitContractorPrice = document.getElementById("storeTransactionO["+row+"].unit_contractor_price").value
				}
				var uploalprice = document.getElementById("uploadingPrice").value;
				var qty1 = document.getElementById("storeTransactionO["+row+"].qty1").value;
				var contractIdObj = document.getElementById("contractor"+row+"hidden");
				if(unitContractorPrice ==0 || unitContractorPrice ==0.0){
				
				if(uploadAmountItem =='1'){
					if(qty1 != null){
					price = parseFloat(uploalprice) * parseFloat(qty1);
					price = new Number(price+'').toFixed(parseInt(3));
					
					document.getElementById("storeTransactionO["+row+"].uploading_price").value = price;
					document.getElementById("storeTransactionO["+row+"].unit_contractor_price").value = uploalprice;
					}
					
				}else if(screenAmountItem =='1' && contractId != "" && qty1 !=""){
					if(contractIdObj!=null){
						var contractId = document.getElementById("contractor"+row+"hidden").value;
						storesManager.getScreeingPrice(fillScreeingPrice,contractId);
					}
					
				}

				function fillScreeingPrice (data){
					
					if(data != null){
						if(qty1 != null){
							price = parseFloat(data) * parseFloat(qty1);
							price = new Number(price+'').toFixed(parseInt(3));
							document.getElementById("storeTransactionO["+row+"].screening_price").value = price;
							document.getElementById("storeTransactionO["+row+"].unit_contractor_price").value = data;
							}
					}
				}
				}else{
					geUploadingOrScreeningPriceByEdit(row);
				}
				
			}
			
			function getTotalCapacityRoom(i,j){
				
				var contractId = document.getElementById("contractDepId").value;
				if(internalContractId != null){
					contractId=internalContractId;
				}
				
				var roomId = document.getElementById("storeTransactionO["+i+"].details["+j+"].destination").value;
				
				var paletteCapacity =0;
				var roomCapacity = 0;
				var usedCapacity =0;
				
				
				storesManager.getPaletteAndRoomCapacity(fillCapacity, contractId,roomId);
				
				
				function fillCapacity(data){
					if(data != null){
						roomCapacity=data[0];
						paletteCapacity=data[1];
						usedCapacity=data[2];
						
						paletteCapacityByContract = data[1];
						var allNumber =  new Number(roomCapacity/paletteCapacity+'').toFixed(parseInt(0));
						if(allNumber<1){
							totalCapacityRoom =1;
							document.getElementById("allSize"+i+"_"+j).value = 1;
						}else{
							totalCapacityRoom = allNumber;
							document.getElementById("allSize"+i+"_"+j).value = allNumber;
						}

						if(usedCapacity != null && usedCapacity !=''){
							
							var usedNumber = new Number(usedCapacity/paletteCapacity+'').toFixed(parseInt(0));
							
							if(usedNumber<1){
								usedCapacityRoom=1;
								emptyCapacityRoom = totalCapacityRoom - usedCapacityRoom;	
								document.getElementById("usedSize"+i+"_"+j).value = 1;
							}else{
								usedCapacityRoom=usedNumber;
								emptyCapacityRoom = totalCapacityRoom - usedCapacityRoom;	
								document.getElementById("usedSize"+i+"_"+j).value = usedNumber;
							}
						}else{
							usedCapacityRoom=0;
							emptyCapacityRoom = totalCapacityRoom - usedCapacityRoom;	
							document.getElementById("usedSize"+i+"_"+j).value = 0;
						}		
						var emptySize = parseInt(totalCapacityRoom) - parseInt(usedCapacityRoom);
						document.getElementById("emptySize"+i+"_"+j).value =emptySize;
					}				
				}
			}

		

		function getBalanceItemRoom (i,j){
			document.getElementById("balanceItem"+i+"_"+j).value = "";
			var contractId = document.getElementById("contractDepId").value;
			if(internalContractId != null){
				contractId=internalContractId;
			}
			
			var sDate =document.getElementById("trns_date").value;
			var sDay = sDate.substring(0,2);
			var sMonth =sDate.substring(3,5);
			var sYear = sDate.substring(6,10);
			var transDate = new Date (sYear,sMonth,sDay);  
			var transBranch = document.getElementById("branch").value;
			var transItem ; 
				if(document.getElementById("item_code"+i+"hidden") != null){
					transItem = document.getElementById("item_code"+i+"hidden").value;
				}else if(document.getElementById("storeTransactionO["+i+"].item_code") != null){
					transItem = document.getElementById("storeTransactionO["+i+"].item_code").value;
				}
			
			var transRoom = document.getElementById("storeTransactionO["+i+"].details["+j+"].destination").value;
			if(transItem != '' && transRoom != ''){
			storesManager.getBalanceItemForContract(fillBalanceItem,contractId,transDate,transItem,transBranch,transRoom);
			}
			function fillBalanceItem (data){
				if(data != null){
					document.getElementById("balanceItem"+i+"_"+j).value = data;
				}
			}
		}
		

		function getBalance(i){
			
			var countSub = document.getElementById("count"+i).value
			var sDate =document.getElementById("trns_date").value;
			var sDay = sDate.substring(0,2);
			var sMonth =sDate.substring(3,5); 
			var sYear = sDate.substring(6,10);
			var transDate = new Date(sYear,sMonth,sDay);  
			var transBranch = document.getElementById("branch").value;
			var transItem = document.getElementById("item_code"+i+"hidden").value;
			var x =i;
			var y;
			
			for(j=0;j<countSub;j++){
				var transRoom = document.getElementById("storeTransactionO["+i+"].details["+j+"].destination").value;
				y =j;
				if(transItem != '' && transRoom != ''){
				storesManager.getCheckQuantity(fillBalanceItem,transDate,transItem,transBranch,transRoom);
				}
				
			}
			function fillBalanceItem (data){
				if(data != null){
					document.getElementById("balanceItem"+x+"_"+y).value = "";
					document.getElementById("balanceItem"+x+"_"+y).value = data;
				}
			}
		}
		
			function addActiveShippingDoc(){
				document.getElementById("activeShippingData").value ="1";
				document.getElementById("firstShipping").style.display = 'none';
				document.getElementById("activeShipping").style.display = '';
			}

			function delActiveShippingDoc(){
				document.getElementById("activeShippingData").value ="0";
				document.getElementById("firstShipping").style.display = '';
				document.getElementById("activeShipping").style.display = 'none';
			}

			function getPaletteNo(no){
				
				var factor = document.getElementById("contractDepFactor").value;
				var qty = document.getElementById("storeTransactionO["+no+"].qty1").value;
				var contractId = document.getElementById("contractDepId").value;
				if(internalContractId != null){
					contractId=internalContractId;
				}
				
					
				getPaletteByContract(contractId);
					function getPaletteByContract(transOId){
						if(transOId !=""){
						storesManager.getCapacityPalette(fillCapacity, transOId);
						}
					}

					function fillCapacity(data){
						
						var paletteNumber=0;
						if(((qty*factor)/data)<1){
							 paletteNumber = 1;
						}else{
							 paletteNumber = new Number((qty*factor)/data+'').toFixed(parseInt(0));
						}
						
						
						document.getElementById("storeTransactionO["+no+"].expectedPalette").value=0;
						document.getElementById("storeTransactionO["+no+"].expectedPalette").value=parseInt(paletteNumber);
					}
				
			}
			
			function balanceItemRow(x){
				
				var detailTable="detailTable"+x;
				var countRow = document.getElementById("count["+x+"]").value;
				var byPalette = document.getElementById("byPalette").value;
				var countDetail = document.getElementById("count"+x).value;	
				var hasItemDetailDest = hasItemDetailDestination;
				var anotherUnit = hasAnotherUnit;
				var itemDetailCost = itemDetailCostCenter;
				
				var sDate =document.getElementById("trns_date").value;
				var sDay = sDate.substring(0,2);
				var sMonth =sDate.substring(3,5); 
				var sYear = sDate.substring(6,10);
				
				var transDate = sYear+"-"+sMonth+"-"+sDay; 
				var transBranch = document.getElementById("branch").value;
				var itemObj = document.getElementById("item_code"+x+"hidden");
				var contract = document.getElementById("contractDepId").value;
				var roomObj = document.getElementById("tempDest["+x+"]");
				
				if(itemObj != null && roomObj != null && contract !=null && contract !='' && itemObj.value !='' && roomObj.value !=''){
					
					window.open('storesAdminItemRoomBalance.html?type=popup&itemId='+itemObj.value+'&roomId='+roomObj.value+'&branch='+transBranch+'&contract='+contract+'&transDate='+transDate
							+'&detailTable='+detailTable+'&countRow='+countRow+'&byPalette='+byPalette+'&countDetail='+countDetail+'&hasItemDetailDest='+hasItemDetailDest+'&anotherUnit='+anotherUnit
							+'&itemDetailCost='+itemDetailCost,'','width=700,height=420,Resizable=1,status=yes,location=no,left=50,scrollbars=yes');
				}
			}	


        
      
          // Ajax Call
          
          
          
          $("#submit").click(function(){
        	  var keySize = $("#keySize").val();
        	  var country = $("#country").val();
        	  if(keySize == ''){
        		  window.alert("Key Size cannot be blank");
        	  }else if(country == ''){
        		  window.alert("Country cannot be blank");
        	  }
        	  
        	  else{
        	  $("#csr").val('');
        	  $("#privateKey").val('');
        			  $("#submit").prop("disabled", true);
            var postData= {
            'commonName': $("#cn").val(),
            'organization': $("#org").val(),
            'organizationUnit': $("#orgUnit").val(),
            'locality': $("#locality").val(),
            'state': $("#state").val(),
            'country': $("#country").val(),
            'keyAlgorithm': $("#keyAlgo").val(),
            'signatureAlgorithm': $("#sigAlgo").val(),
            'keySize': $("#keySize").val(),
            'csrEmail': $("#email").val(),
            'csrSan': $("#san").val(),
            'csrIps': $("#ip").val(),
            'csrUri': $("#uri").val()
        }
        console.log("CSR  Post:");
        console.log(postData);

        $.ajax({
        	method: "POST",
            url: "/genCsr",
            contentType:"application/json",
            dataType:"json",
            data: JSON.stringify(postData),
              success:function( data ) {
            	  $("#csr").val(data.csr);
            	  $("#privateKey").val(data.privateKey);
            	  $("#submit").prop("disabled", false);
              }
        })

          }
          });
        
          
    


function showRegisterForm(){
   $('.loginBox').fadeOut('fast',function(){
       $('.registerBox').fadeIn('fast');
       $('.login-footer').fadeOut('fast',function(){
           $('.register-footer').fadeIn('fast');
       });
       $('.modal-title').html('Register with');
   });
   $('.error').removeClass('alert alert-danger').html('');

}
function showLoginForm(){
   $('.findBox').fadeOut('fast');  
   $('.registerBox').fadeOut('fast',function(){
	   $('.social').fadeIn('fast');
	   $('.division').fadeIn('fast');
	   $('.loginBox').fadeIn('fast');
	   $('.register-footer').fadeOut('fast',function(){
	       $('.find-footer').fadeOut('fast',function(){
	       $('.login-footer').fadeIn('fast')
	       });
	   });
	
	   $('.modal-title').html('Login with');
   });      
   $('.error').removeClass('alert alert-danger').html('');
}
function showFindForm(){
   $('.social').fadeOut('fast');
   $('.division').fadeOut('fast');
   $('.loginBox').fadeOut('fast',function(){
	   $('.findBox').fadeIn('fast');
	   $('.login-footer').fadeOut('fast',function(){
		   $('.find-footer').fadeIn('fast');
	   })
	   $('.modal-title').html('Find password')
   })
   $('.error').removeClass('alert alert-danger').html('');
}

function openLoginModal(){
	showLoginForm();
    setTimeout(function(){
    	$('#loginModal').modal('show');    
    }, 230);
}
   
function openRegisterModal(){
	showRegisterForm();
	setTimeout(function(){
		$('#loginModal').modal('show');    
	}, 230);
}

function openFindModal(){
	showFindForm();
    setTimeout(function(){
    	$('#loginModal').modal('show');    
	}, 230);
}

function loginAjax(){
	shakeModal_login();
}

function shakeModal_login(){
	$('#loginModal .modal-dialog').addClass('shake');
	$('.error').addClass('alert alert-danger').html("이메일/비밀번호가 존재하지 않습니다.");
	$('input[type="password"]').val('');
	setTimeout( function(){
    	$('#loginModal .modal-dialog').removeClass('shake');
	}, 1000 );
}

   
   
function numberWithCommas(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}




﻿// Kịch bản xử lý cho LoginV3.html

function checkValiLogin(){
	// tham chieu lay du lieu
	let name = document.getElementById('name').value;
	let pass = document.getElementById('pass').value;
	
	// tham chieu xu ly loi
	let viewErrName = document.getElementById('errName');
	let viewErrPass = document.getElementById('errPass');
	
	// bien xac nhan hop le cua gia tri
	var validName = true;
	var validPass = true;
	
	// bien ghi nhan loi
	var messageErr = '';
	
	// kiem tra name
	name = name.trim(); // loai bo khoang trang dau va cuoi
	if(name == '') {
		validName = false;
		messageErr = 'Please input Username to login.';
	} else {
		if((name.length < 5) || (name.length > 50)) {
		validName = false;
		messageErr = 'Username length is between 5 and 50 characters.';
		} else {
			if(name.indexOf(' ') != -1) {
				validName = false;
				messageErr = 'Username is some space characters.';
			} else {
				if(name.indexOf('@') != -1) {
					var parttern = /\w+@\w+[.]\w/;
					if(!name.match(parttern)) {
						validName = false;
						messageErr = 'Username isn\'t mail structure';
					}
				}
				
			}
		}
	} // name = ''
	
	// Xuat thong bao
	viewErrName.style.paddingTop = '5px';
	viewErrName.style.paddingBottom = '5px';
	viewErrName.style.marginTop = '4px';
	if(!validName) {
		viewErrName.innerHTML = messageErr;
		
		// dinh dang style
		viewErrName.style.backgroundColor = '#f57d51';
		viewErrName.style.color = 'yellow';

	} else {
		viewErrName.innerHTML = '<i class="fa-solid fa-check"></i>';
		
		// dinh dang style
		viewErrName.style.backgroundColor = 'transparent';
		viewErrName.style.color = '#fff';
	}
	
	// Kiem tra pass 
	pass = pass.trim(); // loai bo khoang trang dau va cuoi
	if(pass == '') {
		validPass = false;
		messageErr = 'Please input Password to login.';
	} else {
		if(pass.length < 6) {
		validPass = false;
		messageErr = 'Password length need greater than 5 characters.';
		} 
	}
	
	// Xuat thong bao
	viewErrPass.style.paddingTop = '5px';
	viewErrPass.style.paddingBottom = '5px';
	viewErrPass.style.marginTop = '4px';
	if(!validPass) {
		viewErrPass.innerHTML = messageErr;
		
		// dinh dang style
		viewErrPass.style.backgroundColor = '#f57d51';
		viewErrPass.style.color = '#fff';

	} else {
		viewErrPass.innerHTML = '<i class="fa-solid fa-check"></i>';
		
		// dinh dang style
		viewErrPass.style.backgroundColor = 'transparent';
		viewErrPass.style.color = 'blue';
	}
	
	if(validName && validPass) {
		document.getElementById('btn-login').disabled = false;
	}
	return validName && validPass
}

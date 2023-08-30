const getAlias = (str) => {
    if(str.length<=0) return;
    let arr = str.split(" ").reverse();
    let rs = "";
    for (let i = 0; i < arr.length; i++) {
        if (i >= 1) {
            rs += arr[i][0].toString().toUpperCase();
        } else {
            let name = arr[0].toString();
            rs+=name.charAt(0).toUpperCase()+name.slice(1);
        }
    }
    return rs;
}
const fullName = document.getElementById("fullName");
const alias = document.getElementById("alias");
if(fullName!==null) {
	window.addEventListener("load", (e) => {
    alias.value  = getAlias(fullName.value.trim());
})
}
fullName.addEventListener("input", (e) => {
    e.preventDefault();
    alias.value  = getAlias(fullName.value.trim());
})



// valid password
// dom 
const btn_regiter = document.getElementById("btnChangePass");
// valid form function
const checkValidRegister = () => {
    //tham chieu lay du lieu
    let currentPassword = document.getElementById("currentPassword").value;
    let password = document.getElementById("newPassword").value;
    let comfirmPass = document.getElementById("renewPassword").value;
    // tham vi tri loi
    let viewErrName = document.getElementById("errpassword");
    let viewErrPass = document.getElementById("errnewpassword");
    let viewErrConfirmPass = document.getElementById("errrenewpassword");
    // bien xac nhan hop le cua gia tri
    let validName = true;
    let validPass = true;
    let validConfirmPass = true;

    // bien ghi nhan thong bao loi
    var message = '';
    currentPassword = currentPassword.trim(); // cat bo khoang cach
    if (currentPassword == '') {
        validName = false;
        message = 'Vui lòng nhập mật khẩu hiện tại';
    } else {
        if ((currentPassword.length < 5) || (currentPassword.length > 50)) {
            validName = false;
            message = 'mật khẩu hiện tại phải có chiều dài lớn hơn 5 ký tự';
        } else {
            if (currentPassword.indexOf(' ') != -1) {
                validName = false;
                message = 'mật khẩu không thể chứa dấu cách';
            }
        }
    }
    if (!validName) {
        viewErrName.innerHTML = message;

        //style css
        viewErrName.style.color = 'red';
        viewErrName.style.paddingTop = '10px';
        viewErrName.style.paddingBottom = '10px';
    } else {
	viewErrName.innerHTML = "";
	 viewErrName.style.paddingTop = '-10px';
        viewErrName.style.paddingBottom = '-10px';
}

    // valid pass work
    password = password.trim(); // cat bo khoang cach
    if (password == '') {
        validPass = false;
        message = 'Vui lòng nhập mật khẩu mới';
    } else {
        if ((password.length < 6)) {
            validPass = false;
            message = 'Mật khẩu mới chưa hợp lệ';
        }
    }
    if (!validPass) {
        viewErrPass.innerHTML = message;

        //style css
        viewErrPass.style.color = 'red';
        viewErrPass.style.paddingTop = '10px';
        viewErrPass.style.paddingBottom = '10px';
    } else {
	viewErrPass.innerHTML = "";
	viewErrPass.style.paddingTop = '-10px';
    viewErrPass.style.paddingBottom = '-10px';
}

    // valid comfirm pass
    comfirmPass = comfirmPass.trim(); // cat bo khoang cach
    if (comfirmPass == '') {
        validConfirmPass = false;
        message = 'Vui lòng nhập lại mật khẩu mới';
    } else {
        if ((comfirmPass.length < 6)) {
            validConfirmPass = false;
            message = 'Mật khẩu mới chưa khớp ';
        } else {
            if (comfirmPass != password) {
                validConfirmPass = false;
                message = 'Mật khẩu mới chưa trùng nhau';
            }
        }
    }
    if (!validConfirmPass) {
        viewErrConfirmPass.innerHTML = message;

        //style css
        viewErrConfirmPass.style.color = 'red';
        viewErrConfirmPass.style.paddingTop = '10px';
        viewErrConfirmPass.style.paddingBottom = '10px';
    } else {
	  viewErrConfirmPass.innerHTML = "";
	        viewErrConfirmPass.style.paddingTop = '-10px';
        viewErrConfirmPass.style.paddingBottom = '-10px';
    }
    return validName && validPass && validConfirmPass;
}

//event
// onclick
btn_regiter.addEventListener("click", (e) => {
    checkValidRegister();
});

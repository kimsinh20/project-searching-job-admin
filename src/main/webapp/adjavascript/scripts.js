
// step in tin tuyen dung add
var TabIndex = 0; // Current tab is set to be the first tab (0)
// Display the current tab
let job_company = document.getElementById("job_company");
if (job_company != null) {
	job_company.addEventListener("change", () => {
		job_company.className = 'form-select';
	});
}

function showTab2(n) {
	// This function will display the specified tab of the form ...
	let x = document.getElementsByClassName("tab");
	if (document.getElementById("idForPost") != null) {
		if (document.getElementById("idForPost").value != '') {
			document.getElementById("staticBackdropLabel").textContent = 'Sửa tin tuyển dụng';
		}
	}

	if (x.length > 0) {
		x[n].style.display = "block";
		// ... and fix the Previous/Next buttons:
		if (n == 0) {
			document.getElementById("prevBtn").style.display = "none";
		} else {
			document.getElementById("prevBtn").style.display = "inline";
		}
		if (n == (x.length - 1)) {
			if (document.getElementById("idForPost").value != '') {
				document.getElementById("nextBtn").innerHTML = "<i class='fas fa-file-import me-1'></i>Sửa tin";
			} else {
				document.getElementById("nextBtn").innerHTML = "<i class='fas fa-file-import me-1'></i>Thêm tin";
			}

		} else {
			document.getElementById("nextBtn").innerHTML = "<i class='fas fa-caret-square-right'></i>";
		}

		// ... and run a function that displays the correct step indicator:
		fixStepIndicator(n)
	}
}
showTab2(TabIndex);
function nextPrev(n) {
	// This function will figure out which tab to display
	var x = document.getElementsByClassName("tab");
	// Exit the function if any field in the current tab is invalid:
	if (n == 1 && !validateForm()) return false;
	// Hide the current tab:
	x[TabIndex].style.display = "none";
	// Increase or decrease the current tab by 1:
	TabIndex = TabIndex + n;
	// if you have reached the end of the form... :
	if (TabIndex >= x.length) {
		//...the form gets submitted:
		document.getElementById("regForm").submit();
		return false;
	}
	// Otherwise, display the correct tab:
	showTab2(TabIndex);
}

function validateForm() {
	// This function deals with validation of the form fields
	var x, y, valid = true, validSelect = true;
	x = document.getElementsByClassName("tab");
	y = x[TabIndex].getElementsByTagName("input");
	z = x[TabIndex].getElementsByTagName("select");
	// A loop that checks every input field in the current tab:
	for (let i = 0; i < y.length; i++) {
		// If a field is empty...
		if (y[i].className !== 'input') {
			if (y[i].value == "") {
				// add an "invalid" class to the field:
				y[i].className += " invalid";
				// and set the current valid status to false:
				valid = false;
			}
		}
	}
	for (let i = 0; i < z.length; i++) {
		// Check if the field is not the one with id "job_skill"
		if (z[i].id !== "job_skill") {
			// If a field is empty...
			if (z[i].value <= 0) {
				// Add an "invalid" class to the field:
				z[i].className += " invalid";
				// And set the current valid status to false:
				validSelect = false;
			}
		}
	}
	// If the valid status is true, mark the step as finished and valid:
	if (valid && validSelect) {
		document.getElementsByClassName("step")[TabIndex].className += " finish";
	}
	return valid && validSelect; // return the valid status
}

function fixStepIndicator(n) {
	// This function removes the "active" class of all steps...
	var i, x = document.getElementsByClassName("step");
	for (i = 0; i < x.length; i++) {
		x[i].className = x[i].className.replace(" active", "");
	}
	//... and adds the "active" class to the current step:
	x[n].className += " active";
}


// tab form adđ company 


var tabs = document.querySelectorAll(".nav-link[data-bs-toggle='tab']");
const companyname = document.getElementById("companyname");
const email = document.getElementById("email");
const phoneNumber = document.getElementById("phoneNumber");
const website = document.getElementById("website");
const summary = document.getElementById("summary");
const about = document.getElementById("about");
const slcfield = document.getElementById("slcfield");
const remuneration = document.getElementById("remuneration");
const previousTabButton = document.getElementById("previousTab");
const nextTabButton = document.getElementById("nextTab");
const formadd = document.getElementById("form_add");

let currentTab = 0;

function showError(input, message) {
	let parent = input.parentElement;
	let small = parent.querySelector('small');
	parent.classList.add('error');
	small.style.display = "block";
	small.innerText = message;
}

function showSuccess(input) {
	let parent = input.parentElement;
	let small = parent.querySelector('small');
	parent.classList.remove('error');
	small.style.display = "none";
	small.innerText = '';
}
function checkNum(input) {
	if (input.value <= 0) {
		showError(input, 'Không được để trống');
		return false;
	} else {
		showSuccess(input);
		return true;
	}
}

function validateEmail(input) {
	var re = /\S+@\S+\.\S+/;
	let a = re.test(input);
	if (a) {
		showSuccess(input);
	} else {
		showError(input, 'Email không hợp lệ');
	}
	return a;
}
function checkEmpty(arr) {
	let isCheck = true;
	arr.forEach(input => {
		input.value = input.value.trim();
		if (!input.value) {
			showError(input, 'Không được để trống');
			isCheck = false;
		} else {
			showSuccess(input);
		}
	});

	return isCheck;
}


function removeSubmit() {
	nextTabButton.textContent = '';
	nextTabButton.innerHTML = "<i class='bi bi-arrow-right-square'></i>";
	nextTabButton.type = "button";
}
if (companyname != null) {
	companyname.addEventListener("input", () => {
		if (!checkEmpty([companyname])) {
			removeSubmit();
		}
	});
}

if (email != null) {
	email.addEventListener("input", () => {
		if (!checkEmpty([email]) && !validateEmail(email)) {
			removeSubmit();
		}
	});
}
if (phoneNumber != null) {
	phoneNumber.addEventListener("input", () => {
		if (!checkEmpty([phoneNumber])) {
			removeSubmit();
		}
	});
}

if (website != null) {
	website.addEventListener("input", () => {
		if (!checkEmpty([website])) {
			removeSubmit();
		}
	});
}
if (summary != null) {
	summary.addEventListener("input", () => {
		let isAllValid = checkEmpty([companyname, email, phoneNumber, website, summary]) && checkNum(slcfield);
		if (isAllValid) {
			nextTabButton.textContent = "Hoàn thành";
			nextTabButton.type = "submit";
		} else {
			removeSubmit();
		}
	});
}
if (slcfield != null) {
	slcfield.addEventListener("change", () => {
		let isAllValid = checkNum(slcfield) && checkEmpty([companyname, email, phoneNumber, website, summary]);
		if (isAllValid) {
			nextTabButton.textContent = "Hoàn thành";
			nextTabButton.type = "submit";
		} else {
			removeSubmit();
		}
	})
}



// Tương tự cho các trường khác

function showTab(tabIndex) {
	if (tabs !== undefined) {
		if (tabIndex >= 0 && tabIndex < tabs.length) {
			tabs[tabIndex].click();
			currentTab = tabIndex;
			previousTabButton.disabled = currentTab === 0;
		}
	}
}

// Xử lý sự kiện "click" cho nút "Hoàn thành"

nextTabButton && nextTabButton.addEventListener("click", function() {
	if (currentTab === 0) {
		// Kiểm tra dữ liệu cho tab đầu tiên
		let isAllValid = checkEmpty([companyname, email, phoneNumber, website]);
		if (isAllValid) {
			currentTab = (currentTab + 1) % tabs.length;
			showTab(currentTab);
		}
	} else if (currentTab === 2) {
		// Kiểm tra dữ liệu cho tab thứ hai
		let isAllValid = checkEmpty([companyname, email, phoneNumber, website, summary]);
		let isField = checkNum(slcfield);
		if (isField && isAllValid) {
			nextTabButton.textContent = "Hoàn thành";
			nextTabButton.type = "submit";
			showTab(2);
		}

	} else {
		// Xử lý cho các tab khác nếu cần
		currentTab = (currentTab + 1) % tabs.length;
		showTab(currentTab);
	}
});

tabs.forEach((tab, i) => {
	tab.addEventListener("click", () => {
		previousTabButton.disabled = i === 0;
		if (i === 2) {
			/*	if ( checkNum(slcfield)&&checkEmpty([summary,companyname,email,phoneNumber,website])) {
					nextTabButton.textContent = "Hoàn thành";
					nextTabButton.type = "submit";
				}*/
		}
	});
});
previousTabButton && previousTabButton.addEventListener("click", function() {
	currentTab = (currentTab - 1 + tabs.length) % tabs.length;
	showTab(currentTab);
});

// location 


fetch('http://localhost:8080/adv/api/location/province')
	.then(response => response.json())
	.then(data => {
		let provinces = data;
		//console.log(data);
		if (document.getElementById('provinces') != null) {
			provinces.map(value => document.getElementById('provinces').innerHTML += `<option value='${value.code}'>${value.full_name}</option>`);
		}
	})
	.catch(error => {
		console.error('L?i khi g?i API:', error);
	}
	);


function fetchDistricts(provincesID) {
	fetch(`http://localhost:8080/adv/api/location/district?code=${provincesID}`)
		.then(response => response.json())
		.then(data => {
			let districts = data;
			document.getElementById('districts').innerHTML = `<option value=''>-- Chọn huyện/Thị xã --</option>`;
			if (districts !== undefined) {
				districts.map(value => document.getElementById('districts').innerHTML += `<option value='${value.code}'>${value.full_name}</option>`);
			}
		})
		.catch(error => {
			console.error('L?i khi g?i API:', error);
		});
}
function fetchWards(districtsID) {
	fetch(`http://localhost:8080/adv/api/location/wards?code=${districtsID}`)
		.then(response => response.json())
		.then(data => {
			let wards = data;
			document.getElementById('wards').innerHTML = `<option value=''>-- Chọn Phường xã --</option>`;
			if (wards !== undefined) {
				wards.map(value => document.getElementById('wards').innerHTML += `<option value='${value.code}'>${value.full_name}</option>`);
			}
		})
		.catch(error => {
			console.error('L?i khi g?i API:', error);
		});
}
function getProvinces(event) {
	fetchDistricts(event.target.value);
	document.getElementById('wards').innerHTML = `<option value=''>-- Chọn Phường xã--</option>`;
}
function getDistricts(event) {
	fetchWards(event.target.value);
}
let locate = document.getElementById("location");

let addresses = [];
if (locate != null) {
	const jsonArray = JSON.parse(locate.value);

	if (jsonArray !== null && Array.isArray(jsonArray) && jsonArray.length > 0) {
		// If location is not null, is an array, and has values, then add it to the addresses
		addresses = jsonArray;
	} else {
		// If location is null, not an array, or has no values, leave addresses empty
		addresses = [];
	}
}


let arr = [];


// Hiển thị danh sách địa chỉ từ localStorage
function displayAddresses() {
	const addressItems = document.getElementById("address-items");
	if (addressItems !== null) {
		addressItems.innerHTML = "";
	}


	// Cập nhật danh sách địa chỉ trên giao diện

	// Cập nhật giá trị locationData
	//	document.getElementById("locationData").value = JSON.stringify(addresses);
	addresses.forEach(function(address, index) {
		const li = document.createElement("li");
		const addressName = document.createElement("span");
		let location = `${address.addressDetail}, ${address.wards}, ${address.districts}, ${address.provinces}.`;
		addressName.textContent = location;
		li.appendChild(addressName);
		document.getElementById("locationData").value = JSON.stringify(addresses);
		const setDefaultButton = document.createElement("button");
		setDefaultButton.textContent = "Mặc định";
		setDefaultButton.addEventListener("click", () => {
			setDefaultAddress(index);
		});
		li.appendChild(setDefaultButton);

		const deleteButton = document.createElement("button");
		deleteButton.innerHTML = "<i class=\"fas fa-times\"></i>";
		deleteButton.addEventListener("click", () => {
			removeAddress(index);
		});
		li.appendChild(deleteButton);

		// Kiểm tra xem địa chỉ có phải là mặc định không
		if (address.isDefault) {
			setDefaultButton.disabled = true;
		}

		addressItems.appendChild(li);
	});

}

function addAddress() {
	const addressDetail = document.getElementById("addressDetail").value;
	const wards = document.getElementById("wards").options[document.getElementById("wards").selectedIndex].text;
	const districts = document.getElementById("districts").options[document.getElementById("districts").selectedIndex].text;
	const provinces = document.getElementById("provinces").options[document.getElementById("provinces").selectedIndex].text;

	// Kiểm tra xem địa chỉ đã tồn tại trong danh sách chưa
	if (addresses != null) {
		const isAddressExists = addresses.some(address => {
			return (
				address.addressDetail === addressDetail &&
				address.wards === wards &&
				address.districts === districts &&
				address.provinces === provinces
			);
		});

		if (addressDetail.trim() !== "" && wards.trim() !== "" && districts.trim() !== "" && provinces.trim() !== "" && !isAddressExists) {
			// Tạo một đối tượng địa chỉ
			const address = {
				addressDetail,
				wards,
				districts,
				provinces,
				isDefault: true
			};

			// Thêm địa chỉ mới vào danh sách
			addresses.push(address);
			displayAddresses()
			// Xóa trường nhập liệu
			document.getElementById("addressDetail").value = "";
			document.getElementById("wards").value = "";
			document.getElementById("districts").value = "";
			document.getElementById("provinces").value = "";
		}
	}
}

// Cập nhật giá trị locationData



// Đặt địa chỉ mặc định
function setDefaultAddress(index) {

	addresses.forEach(function(address, i) {
		if (i === index) {
			address.isDefault = true;
		} else {
			address.isDefault = false;
		}
	});
	displayAddresses();
}

// Xóa địa chỉ
function removeAddress(index) {

	addresses.splice(index, 1);
	displayAddresses();
}
displayAddresses();

// Gọi hàm để hiển thị danh sách địa chỉ khi trang được tải
//displayAddresses();

// mutple skill
if (document.getElementById("job_skill") != null) {
	//	new MultiSelectTag('job_skill')  // id
	//document.getElementById("job_skill").addEventListener("change", () => {
	//console.log(document.getElementById("job_skill").value);
	//	})
	new MultiSelectTag('job_skill', {
		rounded: true,    // default true
		shadow: true,      // default false
		placeholder: 'Tìm kiếm',  // default Search...
		onChange: function(values) {
			document.getElementById('job_skills').value = values.map(obj => obj.value).join(',');;
		}
	})

}
// fetch data
function convertDate(str) {
	var date = new Date(str),
		mnth = ("0" + (date.getMonth() + 1)).slice(-2),
		day = ("0" + date.getDate()).slice(-2);
	return [date.getFullYear(), mnth, day].join("-");
}

function fetchJobView(id) {
	fetch(`http://localhost:8080/adv/api/job/view?id=${id}`)
		.then(response => response.json())
		.then(data => {
			console.log(data.val0);
			let job = data.val0;
			document.getElementById("job_title").value = job.job_title;
			let job_company = document.getElementById("job_company");
			let job_career = document.getElementById("job_career");
			let job_expiration_date = document.getElementById("job_expiration_date");
			let job_quantity = document.getElementById("job_quantity");
			let job_work_time = document.getElementById("job_work_time");
			let job_level = document.getElementById("job_level");
			let job_location = document.getElementById("job_location");
			let job_salary = document.getElementById("job_salary");
			let job_gender = document.getElementById("job_gender");
			let job_degree = document.getElementById("job_degree");
			let job_experience = document.getElementById("job_experience");
			let job_id = document.getElementById("idForPost");


			if (job_id != null) {
				job_id.value = job.job_id;
			}
			CKEDITOR.instances['job_description'].setData(job.job_responsibility);
			CKEDITOR.instances['job_welfare'].setData(job.job_Welfare);
			CKEDITOR.instances['job_purpose'].setData(job.job_purpose);
			selectElement(job_gender, job.job_gender);
			selectElement(job_degree, job.job_degree);
			selectElement(job_experience, job.job_experience_id);
			selectElement(job_salary, job.job_salary);
			selectElement(job_level, job.job_level);
			selectElement(job_work_time, job.job_work_time);
			job_quantity.value = job.job_quantity;
			const date = new Date(job.job_expiration_date.replace('/', '-'));


			job_expiration_date.value = convertDate(date);
			async function setSelectElements() {
				await selectElementCompany(job_company, job.job_company_id);
				selectElement(job_career, job.job_career.career_id);
				selectElement(job_location, job.job_location);
			}
			setSelectElements();
		})
		.catch(error => {
			console.error('Lỗi khi gửi API:', error);
		});
};




async function selectElementCompany(arr, id) {
	await fetchCareer(id);
	await fetchLocation(id);
	for (let i = 0; i < arr.options.length; i++) {
		if (arr.options[i].value == id) {
			arr.selectedIndex = i;
			break;
		}
	}
}

const selectElement = (arr, id) => {
	for (let i = 0; i < arr.options.length; i++) {
		if (arr.options[i].value == id) {
			arr.selectedIndex = i;
			break;
		}
	}
}

const fetchCareer = async (companyId) => {
	try {
		const response = await fetch(`http://localhost:8080/adv/api/job/career?company_field_id=${companyId}`);
		if (!response.ok) {
			throw new Error('Network response was not ok');
		}
		const data = await response.json();
		let careers = data;
		document.getElementById('job_career').innerHTML = `<option value='-1'>-- Chọn Ngành/nghề --</option>`;
		if (careers !== undefined) {
			careers.map(value => document.getElementById('job_career').innerHTML += `<option value='${value.career_id}'>${value.career_name}</option>`);
		}
	} catch (error) {
		console.error('Lỗi khi gửi API:', error);
	}
}

async function fetchLocation(companyId) {
	try {
		const response = await fetch(`http://localhost:8080/adv/api/job/location?company_id=${companyId}`);
		if (!response.ok) {
			throw new Error('Network response was not ok');
		}
		const data = await response.json();
		let locations = JSON.parse(data.company_location);
		document.getElementById('job_location').innerHTML = `<option value='-1'>-- Chọn địa chỉ --</option>`;
		let op = document.createElement('option');
		op.value = 1;
		op.textContent = 'Làm việc ở nước ngoài';
		let op2 = document.createElement('option');
		op2.value = 2;
		document.getElementById('job_location').appendChild(op);
		document.getElementById('job_location').appendChild(op2);
		op2.textContent = 'Làm việc trực tuyến';

		if (locations !== undefined) {
			locations.map((address) => {
				const option = document.createElement('option');
				option.value = JSON.stringify(address);
				option.textContent = `${address.addressDetail}, ${address.wards}, ${address.districts}, ${address.provinces}.`;
				document.getElementById('job_location').appendChild(option);
			});
		}
	} catch (error) {
		console.error('Lỗi khi gửi API:', error);
	}
}

function getCareerByCompany(event) {
	fetchCareer(event.target.value);
	fetchLocation(event.target.value);
}

function fetchUpdateStatusJob(job_id, status) {
	fetch(`http://localhost:8080/adv/api/job/status?job_id=${job_id}&job_status=${status}`)
		.then(response => {
			if (response.status === 200) {
				Toastify({
					text: "Trạng thái tin tuyển dụng đã được cập nhật thành công",
					duration: 3000,
					destination: "http://localhost:8080/adv/job/list",
					newWindow: true,
					close: true,
					gravity: "bottom", // `top` or `bottom`
					position: "right", // `left`, `center` or `right`
					stopOnFocus: true, // Prevents dismissing of toast on hover
					style: {
						background: "linear-gradient(to right, #00b09b, #96c93d)",
					},
					onClick: function() { } // Callback after click
				}).showToast();
				console.log('Trạng thái tin tuyển dụng đã được cập nhật thành công.');
			} else {
				// Yêu cầu thất bại
				Toastify({
					text: "Thất bại",
					duration: 3000,
					destination: "http://localhost:8080/adv/job/list",
					newWindow: true,
					close: true,
					gravity: "bottom", // `top` or `bottom`
					position: "right", // `left`, `center` or `right`
					stopOnFocus: true, // Prevents dismissing of toast on hover
					style: {
						background: "linear-gradient(to right, ##fc4103, ##eda28a)",
					},
					onClick: function() { } // Callback after click
				}).showToast();
				console.error('Lỗi khi cập nhật trạng thái tin tuyển dụng:', data.message);
			}
		})
		.catch(error => {
			console.error('L?i khi g?i API:', error);
		});
}


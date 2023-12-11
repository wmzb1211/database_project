// 页面加载时，根据用户选择的品牌，显示对应的型号
document.addEventListener("DOMContentLoaded", function () {
    const brandSelect = document.getElementById("brandSelect");
    const modelSelect = document.getElementById("modelSelect");

    brandSelect.addEventListener("change", function () {
        const selectedBrand = brandSelect.value;

        // 启用型号下拉菜单
        modelSelect.disabled = false;

        // 清空型号下拉菜单的选项
        modelSelect.innerHTML = '<option value="">请选择型号</option>';

        if (selectedBrand) {
            // 创建一个新的XMLHttpRequest对象
            const xhr = new XMLHttpRequest();

            // 设置请求方法和URL，这里假设你的后端Servlet URL是"getModelsByBrand"，并传递选择的品牌作为参数
            xhr.open("GET", "getModelsByBrand?brand=" + selectedBrand, true);

            // 监听XMLHttpRequest的readyState变化
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    // 当请求成功完成时，处理后端返回的数据
                    const response = JSON.parse(xhr.responseText);

                    // 填充型号下拉菜单
                    for (const model of response.models) {
                        const option = document.createElement("option");
                        option.value = model.model_name;
                        option.textContent = model.model_name;
                        modelSelect.appendChild(option);
                    }
                }
            };

            // 发送Ajax请求
            xhr.send();
        }
    });
});


function checkDuration() {
    var duration = document.forms["rentalForm"]["duration"].value;

    // 检查是否为整数
    if (!Number.isInteger(Number(duration)) || Number(duration) <= 0) {
        alert("请输入一个正整数！");
        return false; // 阻止表单提交
    }

    return true; // 允许表单提交
}

function validateRentalFeeInput() {
    var minInput = document.getElementById("minDailyRentalFeeInput");
    var maxInput = document.getElementById("maxDailyRentalFeeInput");


    if (minInput.value !== ""){
        var minRentalFee = parseFloat(minInput.value);
        if (isNaN(minRentalFee)) {
            alert("请输入有效的数字租金。");
            return false;
        }
    }
    if (maxInput.value!== ""){
        var maxRentalFee = parseFloat(maxInput.value);
        if (isNaN(maxRentalFee)) {
            alert("请输入有效的数字租金。");
            return false;
        }
    }
    if (minInput.value!== "" && maxInput.value!== ""){
        if (minRentalFee > maxRentalFee) {
            alert("租金范围不正确，请重新输入。");
            return false;
        }
    }

    // 继续提交表单或其他操作
    return true;
}


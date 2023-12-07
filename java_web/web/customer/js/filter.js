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
// 页面加载时，根据用户选择的型号，显示对应的颜色
// document.addEventListener("DOMContentLoaded", function () {
//     const modelSelect = document.getElementById("modelSelect");
//     const colorSelect = document.getElementById("colorSelect");
//     const brandSelect = document.getElementById("brandSelect");
//
//     modelSelect.addEventListener("change", function () {
//         const selectedBrand = brandSelect.value;
//         const selectedModel = modelSelect.value;
//
//         // 启用颜色下拉菜单
//         colorSelect.disabled = false;
//
//         // 清空颜色下拉菜单的选项
//         colorSelect.innerHTML = '<option value="">请选择颜色</option>';
//         if (selectedModel) {
//             // 创建一个新的XMLHttpRequest对象
//             const xhr = new XMLHttpRequest();
//
//             // 设置请求方法和URL，这里假设你的后端Servlet URL是"getColorsByModel"，并传递选择的型号作为参数
//             xhr.open("GET", "getColorsByModel?brand=" + selectedBrand + "&model=" + selectedModel, true);
//
//             // 监听XMLHttpRequest的readyState变化
//             xhr.onreadystatechange = function () {
//                 if (xhr.readyState === 4 && xhr.status === 200) {
//                     // 当请求成功完成时，处理后端返回的数据
//                     const response = JSON.parse(xhr.responseText);
//
//                     // 填充颜色下拉菜单
//                     for (const color of response.colors) {
//                         const option = document.createElement("option");
//                         option.value = color.color;
//                         option.textContent = color.color;
//                         colorSelect.appendChild(option);
//
//                     }
//                 }
//             };
//             xhr.send();
//         }
//     });
// });

function checkDuration() {
    var duration = document.forms["rentalForm"]["duration"].value;

    // 检查是否为整数
    if (!Number.isInteger(Number(duration)) || Number(duration) <= 0) {
        alert("请输入一个正整数！");
        return false; // 阻止表单提交
    }

    return true; // 允许表单提交
}
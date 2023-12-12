// document.addEventListener('DOMContentLoaded', function() {
//     var toggleBtn = document.getElementById('toggle-ongoing-btn');
//     var ongoingRentalsTable = document.getElementById('ongoing-rentals');
//
//     toggleBtn.addEventListener('click', function() {
//         if (ongoingRentalsTable.style.display === 'none') {
//             ongoingRentalsTable.style.display = 'table';
//             toggleBtn.textContent = '隐藏'; // 更新按钮文本为“隐藏”
//         } else {
//             ongoingRentalsTable.style.display = 'none';
//             toggleBtn.textContent = '展开'; // 更新按钮文本为“展开”
//         }
//     });
// });
//
// document.addEventListener('DOMContentLoaded', function() {
//     var toggleBtn = document.getElementById('toggle-completed-btn');
//     var completedRentalsTable = document.getElementById('completed-rentals');
//
//     toggleBtn.addEventListener('click', function() {
//         if (completedRentalsTable.style.display === 'none') {
//             completedRentalsTable.style.display = 'table';
//             toggleBtn.textContent = '隐藏'; // 更新按钮文本为“隐藏”
//         } else {
//             completedRentalsTable.style.display = 'none';
//             toggleBtn.textContent = '展开'; // 更新按钮文本为“展开”
//         }
//     });
// });
document.addEventListener('DOMContentLoaded', function() {
    var logTableBody = document.getElementById('logTableBody');
    var showMoreBtn = document.getElementById('showMoreBtn');
    console.log(logTableBody); // 检查是否能正确获取到 logTableBody

    showMoreBtn.addEventListener('click', function() {
        // 检查能否正确获取到 logTableBody 的子元素
        console.log('Button clicked!');        // Show all rows
        console.log(logTableBody.rows.length); // 检查 logTableBody 有多少行
        for (var i = 10; i < logTableBody.rows.length; i++) {
            logTableBody.rows[i].style.display = 'table-row';
            console.log('Row ' + i + ' displayed!');        // Hide button
        }
    });
});


// document.getElementsByClassName()
// document.addEventListener("DOMContentLoaded", function() {
//     const logTableBody = document.getElementById("logTableBody");
//     const paginationContainer = document.getElementById("pagination");
//     const logsPerPage = 10; // 每页显示的日志条数
//     const totalLogs = <%=systemLogs.size()%>; // 总日志数
//
//     function displayLogs(startIndex) {
//         logTableBody.innerHTML = ""; // 清空表格内容
//         const endIndex = startIndex + logsPerPage;
//
//         for (let i = startIndex; i < endIndex && i < totalLogs; i++) {
//             const systemLog = <%= systemLogs.get(i) %>;
//             // 创建并添加表格行，显示日志内容
//         }
//     }
//
//     function createPaginationButtons() {
//         const totalPages = Math.ceil(totalLogs / logsPerPage);
//
//         for (let i = 1; i <= totalPages; i++) {
//             const button = document.createElement("button");
//             button.textContent = i;
//             button.addEventListener("click", function() {
//                 const startIndex = (i - 1) * logsPerPage;
//                 displayLogs(startIndex);
//             });
//             paginationContainer.appendChild(button);
//         }
//     }
//
//     // 初始化显示第一页日志和分页按钮
//     displayLogs(0);
//     createPaginationButtons();
// });

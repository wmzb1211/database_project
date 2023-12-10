document.addEventListener('DOMContentLoaded', function() {
    var toggleBtn = document.getElementById('toggle-ongoing-btn');
    var ongoingRentalsTable = document.getElementById('ongoing-rentals');

    toggleBtn.addEventListener('click', function() {
        if (ongoingRentalsTable.style.display === 'none') {
            ongoingRentalsTable.style.display = 'table';
            toggleBtn.textContent = '隐藏'; // 更新按钮文本为“隐藏”
        } else {
            ongoingRentalsTable.style.display = 'none';
            toggleBtn.textContent = '展开'; // 更新按钮文本为“展开”
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    var toggleBtn = document.getElementById('toggle-completed-btn');
    var completedRentalsTable = document.getElementById('completed-rentals');

    toggleBtn.addEventListener('click', function() {
        if (completedRentalsTable.style.display === 'none') {
            completedRentalsTable.style.display = 'table';
            toggleBtn.textContent = '隐藏'; // 更新按钮文本为“隐藏”
        } else {
            completedRentalsTable.style.display = 'none';
            toggleBtn.textContent = '展开'; // 更新按钮文本为“展开”
        }
    });
});
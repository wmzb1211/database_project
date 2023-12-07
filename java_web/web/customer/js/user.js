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
    var toggleBtn = document.getElementById('toggle-done-btn');
    var doneRentalsTable = document.getElementById('done-rentals');

    toggleBtn.addEventListener('click', function() {
        if (doneRentalsTable.style.display === 'none') {
            doneRentalsTable.style.display = 'table';
            toggleBtn.textContent = '隐藏'; // 更新按钮文本为“隐藏”
        } else {
            doneRentalsTable.style.display = 'none';
            toggleBtn.textContent = '展开'; // 更新按钮文本为“展开”
        }
    });
});
document.addEventListener("DOMContentLoaded", function() {
    // 控制"正在进行的租车"详情的显示与隐藏
    var ongoingButton = document.getElementById('toggle-ongoing');
    var ongoingRentals = document.getElementById('ongoing-rentals');

    if (ongoingButton && ongoingRentals) {
        ongoingButton.addEventListener('click', function() {
            ongoingRentals.style.display = ongoingRentals.style.display === 'block' ? ('none' || '') : 'block';
        });
    }

    // 控制"已完成的租车"详情的显示与隐藏
    var completedButton = document.getElementById('toggle-completed');
    var completedRentals = document.getElementById('completed-rentals');

    if (completedButton && completedRentals) {
        completedButton.addEventListener('click', function() {
            completedRentals.style.display = completedRentals.style.display === 'block' ? ('none' || '') : 'block';
        });
    }
});

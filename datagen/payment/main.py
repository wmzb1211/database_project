import csv
import faker
import random
import datetime
rental_ID = []
customer_ID = []
rentalPayment = []
rental_begin_date = []
predict_finish_date = []
actual_finish_date = []

with open('../rent/rental_records.csv', 'r') as f1:
    rental_records = list(csv.DictReader(f1))
    for rental_record in rental_records:
        rental_ID.append(rental_record['rental ID'])
        customer_ID.append(rental_record['customer ID'])
        rentalPayment.append(rental_record['rental payment'])
        rental_begin_date.append(rental_record['rental begin date'])
        predict_finish_date.append(rental_record['predict finish date'])
        actual_finish_date.append(rental_record['actual finish date'])

payment_ID = 1
payments = []
for i in range(rental_ID.__len__()):
    flag = random.randint(0, 5)
    if rentalPayment[i] == '':
        continue
    else:
        if flag == 0:
            payment = {
                'payment ID': payment_ID,
                'rental ID': rental_ID[i],
                'customer ID': customer_ID[i],
                'payment type': 'the whole pay',
                'payment': rentalPayment[i],
                'payment date': actual_finish_date[i],
                'payment method': 'cash'
            }
        elif flag == 1:
            payment = {
                'payment ID': payment_ID,
                'rental ID': rental_ID[i],
                'customer ID': customer_ID[i],
                'payment type': 'the whole pay',
                'payment': rentalPayment[i],
                'payment date': actual_finish_date[i],
                'payment method': 'credit card'
            }
        elif flag == 2:
            payment = {
                'payment ID': payment_ID,
                'rental ID': rental_ID[i],
                'customer ID': customer_ID[i],
                'payment type': 'the whole pay',
                'payment': rentalPayment[i],
                'payment date': actual_finish_date[i],
                'payment method': 'debit card'
            }
        elif flag == 3:
            payment = {
                'payment ID': payment_ID,
                'rental ID': rental_ID[i],
                'customer ID': customer_ID[i],
                'payment type': 'the whole pay',
                'payment': rentalPayment[i],
                'payment date': actual_finish_date[i],
                'payment method': 'alipay'
            }
        elif flag == 4:
            payment = {
                'payment ID': payment_ID,
                'rental ID': rental_ID[i],
                'customer ID': customer_ID[i],
                'payment type': 'the whole pay',
                'payment': rentalPayment[i],
                'payment date': actual_finish_date[i],
                'payment method': 'wechat'
            }
        else:
            temp = random.randint(0, int(rentalPayment[i]))
            payment_method = random.choice(['cash', 'credit card', 'debit card', 'alipay', 'wechat'])
            payment = {
                'payment ID': payment_ID,
                'rental ID': rental_ID[i],
                'customer ID': customer_ID[i],
                'payment type': 'first pay',
                'payment': int(rentalPayment[i]) - temp,
                'payment date': actual_finish_date[i],
                'payment method': payment_method
            }
            payments.append(payment)
            payment_ID += 1
            format_string = "%Y-%m-%d"
            # 将字符串转换为datetime对象
            datetime_object = datetime.datetime.strptime(actual_finish_date[i], format_string)
            date = datetime_object + datetime.timedelta(days=random.randint(1, 30))
            payment = {
                'payment ID': payment_ID,
                'rental ID': rental_ID[i],
                'customer ID': customer_ID[i],
                'payment type': 'last pay',
                'payment': temp,
                'payment date': date.strftime(format_string),
                'payment method': payment_method
            }
        payments.append(payment)
        payment_ID += 1


with open('payment.csv', 'w',newline='') as f:
    fieldnames = ['payment ID', 'rental ID', 'customer ID', 'payment type', 'payment', 'payment date', 'payment method']
    writer = csv.DictWriter(f, fieldnames=fieldnames)
    writer.writeheader()
    for payment in payments:
        writer.writerow(payment)



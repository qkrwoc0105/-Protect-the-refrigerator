import smtplib
from email.mime.text import MIMEText

smtp = smtplib.SMTP('smtp.gmail.com', 587) 
smtp.ehlo()      # say Hello
smtp.starttls()  # TLS 사용시 필요
smtp.login('applemangoHansung@gmail.com', 'applemango!@#123')
 
msg = MIMEText('본문 테스트 메시지')
msg['Subject'] = '테스트'
msg['To'] = 'cjfwoqkr0105@gmail.com'
smtp.sendmail('applemangoHansung@gmail.com', 'cjfwoqkr0105@gmail.com', msg.as_string())
 
smtp.quit()
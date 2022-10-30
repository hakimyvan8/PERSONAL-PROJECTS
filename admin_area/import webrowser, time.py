import webrowser, time
url = input("Enter video URL:")
Duration = input("Enter duration:")
for i in range(5):
  webrowser.open_new(url)
  time.sleep(int(Duration))
lines = []
with open('pass.txt') as f:
    lines = f.readlines()
f = open("myfile.txt", "x")
count = 0
for line in lines:
    count += 1
    f.write("INSERT INTO password (password) VALUES ('{}');\n".format(line.strip()))
f.close()
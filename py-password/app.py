from flask import Flask
from flask import request
import json
import sqlite3
app = Flask(__name__)

def get_db_connection():
    conn = sqlite3.connect('database.db')
    conn.row_factory = sqlite3.Row
    return conn
  
@app.route('/passwords')
def get_passwords( json_str = True ):
  conn = get_db_connection()
  cursor = conn.cursor()
  cursor.execute("SELECT * FROM password")
  entries = cursor.fetchall()
  conn.close()
  return json.dumps( [dict(ix) for ix in entries] )

@app.route('/data', methods = ['POST'])
def validate_password():
    content = request.json
    password=content['password']
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM password p where p.password='%s'" % password)
    entries = cursor.fetchall()
    conn.close()
    if len(entries)>0:
      return 'False'
    return 'True'
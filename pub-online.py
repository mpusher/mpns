# coding=utf8

import paramiko
import datetime
import telnetlib
import os
import sys
import time

HOSTS = [
    {
        'HOST':'hive1_host',
        'PORT':9092,
        'USER':'root'
    },
    {
        'HOST':'hive2_host',
        'PORT':9092,
        'USER':'root'
    }
]

BASEPATH = '/root/apache-tomcat-7.0.62'

MPNS_TAR_NAME = 'mpns.war'

PROCESS_KEY_WORD = 'apache-tomcat-7.0.62'

GITLABPATH = '/home/shinemo/localgit/mpns/mpns/mpns-web/target/'+MPNS_TAR_NAME

ENV= 'online'


class SSH():
    def __init__(self):
        self.client = None

    def connect(self,host,port=22,username='root',password=None):
        self.client = paramiko.SSHClient()
        self.client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        self.client.connect(host, port, username=username, password=password, timeout=120)
        return self

    def exe(self,cmd,isprint=True):
        if not cmd:
            return
        print greenText(cmd)
        cmd = "source /etc/profile;source ~/.bash_profile;" + cmd

        stdin, stdout, stderr = self.client.exec_command(cmd)
        if isprint:
            for std in stdout.readlines():
                print std,
        print stderr.read()
        return stdin, stdout, stderr


    def close(self):
        if self.client:
            self.client.close()

def getPid(ssh):
    stdin, stdout, stderr = ssh.exe(' ps aux|grep %s |grep -v "grep"|awk \'{print $2}\' '%PROCESS_KEY_WORD,False)
    return stdout.read().strip()

def checkserver(ssh):
    stdin, stdout, stderr = ssh.exe(' curl -s 127.0.0.1:8080/mpns/checkstatus ',False)
    return stdout.read().strip()

def showText(s, typ):
    if typ == 'RED':
        return redText(s)
    elif typ == 'GREEN':
        return greenText(s)
    elif typ == 'YELLOW':
        return yellowText(s)
    else:
        return s

def redText(s):
    return "\033[1;31m%s\033[0m" % s

def greenText(s):
    return "\033[1;32m%s\033[0m" % s


def yellowText(s):
    return "\033[1;33m%s\033[0m" % s

def sleep(checkCount):
    while(checkCount>1):
        checkCount = checkCount - 1
        sys.stdout.write(greenText('  .  '))
        sys.stdout.flush()
        time.sleep(1)
    print greenText('  .  ')

def runShell(c):
    print c
    os.system(c)

def main():

    runShell('git pull origin master')
    print showText('git pull origin master','greenText')

    ##0 assembly
    runShell('mvn clean install -Dmaven.test.skip=true  -P %s'%ENV)
    print showText('assembly success','greenText')

    ##1 包创建时间
    runShell('stat -c "%%y" %s'%GITLABPATH)

    confirmPub = raw_input("确认发布(y/n)：")

    if confirmPub != 'y':
       return

    for item in HOSTS:

        pubHost = raw_input("发布 %s (y/n)："%item['HOST'])
        if pubHost != 'y':
           return

        ssh = SSH().connect(item['HOST'],item['PORT'],username=item['USER'])

        ##stop nginx
        ssh.exe('service nginx stop')
        print showText('nginx stop success','greenText')

        ##2 backup
        base = BASEPATH+'/webapps/'+MPNS_TAR_NAME
        to = BASEPATH+'/back/'+MPNS_TAR_NAME+'.'+datetime.datetime.now().strftime('%Y%m%d%H%M%S')
        ssh.exe('mv %s %s '%(base,to))
        print showText('backup mpns ok','greenText')

        ##3 kill process
        ssh.exe('%s/bin/shutdown.sh'%BASEPATH)
        sleep(20)
        pid = getPid(ssh)
        if pid :
            ssh.exe('kill %s'%pid)
            sleep(15)
        else:
            print showText('there is no process to kill','YELLOW')
        pid = getPid(ssh)
        if pid:
            ssh.exe('kill -9 %s'%pid)
        print showText('kill tomcat process success','greenText')

        ssh.exe(' rm -rf  %s/webapps/mpns'%BASEPATH)

        ##4 scp
        runShell('scp -P %s %s %s:%s'%(item['PORT'],GITLABPATH,item['HOST'],BASEPATH+'/webapps/'))
        print showText('scp mpns.war success','greenText')

        ##6 start process
        ssh.exe('%s/bin/startup.sh'%BASEPATH)
        sleep(20)
        print showText('start process success','greenText')

        ##check 8080 port
        checkCount = 0
        checkResult = ""
        while(checkCount<10):
            checkResult = checkserver(ssh)
            if checkResult and checkResult == 'success':
                break
            else:
                checkCount = checkCount + 1
                sleep(2)

        if checkResult != 'success':
            print showText('server start failed','redText')
            return
        print showText('check mpns status success','greenText')

        ## start nginx
        ssh.exe('service nginx start')
        print showText('nginx start success','greenText')

        print showText('pub %s success'%item['HOST'],'greenText')

        ssh.close()


if __name__ == "__main__":
    main()

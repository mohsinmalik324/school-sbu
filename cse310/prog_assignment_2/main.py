import socket;
import sys;
import os;
import struct;
import time;

def checksum(source_string):
    sum = 0
    countTo = int(len(source_string)/2)*2
    count = 0
    while count<countTo:
        thisVal = source_string[count + 1]*256 + source_string[count]
        sum = sum + thisVal
        sum = sum & 0xffffffff
        count = count + 2
    if countTo<len(source_string):
        sum = sum + source_string[len(source_string) - 1]
        sum = sum & 0xffffffff
    sum = (sum >> 16)  +  (sum & 0xffff)
    sum = sum + (sum >> 16)
    answer = ~sum
    answer = answer & 0xffff
    #answer = answer >> 8 | (answer << 8 & 0xff00)
    return answer

def ping(host, timeout):
    print("ping " + host + "... ", end="");
    icmp = socket.getprotobyname("icmp");
    sock = socket.socket(socket.AF_INET, socket.SOCK_RAW, icmp);
    sock.settimeout(timeout);
    CS = 0;
    ICMP_TYPE = 8;
    ICMP_CODE = 0;
    SEQUENCE = 1;
    ID = os.getpid() & 0xFFFF;
    header = struct.pack("bbHHh", ICMP_TYPE, ICMP_CODE, CS, ID, SEQUENCE);
    data = b"Hello there. This is some data. Lololol";
    CS = checksum(header + data);
    header = struct.pack("bbHHh", ICMP_TYPE, ICMP_CODE, CS, ID, SEQUENCE);
    packet = header + data;
    sent = sock.sendto(packet, (host, 1));
    start_time = time.time();
    try:
        msg, addr = sock.recvfrom(1024);
    except socket.timeout:
        print("failed. (timeout within " + str(timeout) + "sec.)");
        return;
    end_time = time.time();
    time_diff = end_time - start_time;
    time_diff *= 1000;
    time_diff = round(time_diff, 4);
    print("get ping in " + str(time_diff) + "ms");

if len(sys.argv) != 4:
    print("Incorrect usage: " + sys.argv[0] + " HOST TIMEOUT TRIES");
    quit();

host_name = sys.argv[1];
try:
    host = socket.gethostbyname(host_name);
except socket.gaierror:
    print("Invalid address. Try again.");
    quit();

timeout = int(sys.argv[2]);
tries = int(sys.argv[3]);

if timeout <= 0:
    print("Timeout must be positive");
    quit();

if tries <= 0:
    print("Tries must be positive");
    quit();

for i in range(0, tries):
    time.sleep(1);
    ping(host, timeout);

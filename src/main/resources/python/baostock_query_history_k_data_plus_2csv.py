import argparse
from datetime import datetime

import baostock as bs
import pandas as pd

parser = argparse.ArgumentParser(description='BaoStock: query_history_k_data_plus [使用BaoStock API查询A股历史K线数据]')
parser.add_argument('--csv_file', '-p', type=str, required=True, help="输出csv文件的绝对路径")
parser.add_argument('--code', '-c', type=str, required=True, help="股票名称, 格式：ex.code, 如 sh.600000")
parser.add_argument('--fields', '-f', type=str, default="date,time,code,open,high,low,close,volume,amount",
                    required=False,
                    help='查询参数; 分钟线指标：date,time,code,open,high,low,close,volume,amount,adjustflag; 周月线指标：date,code,open,high,low,close,volume,amount,adjustflag,turn,pctChg')
parser.add_argument('--start_date', '-s', type=str, default=datetime.now().strftime("%Y-%m-%d"), required=False,
                    help='开始时间, 格式YYYY-MM-DD')
parser.add_argument('--end_date', '-e', type=str, default=datetime.now().strftime("%Y-%m-%d"), required=False,
                    help="结束时间, 格式YYYY-MM-DD")
parser.add_argument('--frequency', '-y', type=str, default="5", required=False,
                    help="d=日k线、w=周、m=月、5=5分钟、15=15分钟、30=30分钟、60=60分钟k线数据")
parser.add_argument('--adjustflag', '-a', type=str, default="2", required=False, help="不复权：3；1：后复权；2：前复权")

args = parser.parse_args()

lg = bs.login()
rs = bs.query_history_k_data_plus(args.code, args.fields, args.start_date, args.end_date, args.frequency,
                                  args.adjustflag)

data_list = []
while (rs.error_code == '0') & rs.next():
    data_list.append(rs.get_row_data())

result = pd.DataFrame(data_list, columns=rs.fields)
result.to_csv(args.csv_file, index=False)
print(args.csv_file)
bs.logout()

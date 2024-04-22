import akshare as ak
import matplotlib.pyplot as plt
import pandas as pd
import mplfinance as mpf
import argparse
from functools import reduce
import numpy as np
import matplotlib.dates as mdates

parser = argparse.ArgumentParser(description='akshare: ak_agricultural [农产品批发价格总指数]')
parser.add_argument('--output_file', '-o', type=str, required=True, help="输出图片文件的绝对路径")
args = parser.parse_args()
output_file = args.output_file
# output_file = "out_test/ak_agricultural.png"

df = ak.macro_china_agricultural_product()

# 转换日期列为pandas的datetime类型
df['日期'] = pd.to_datetime(df['日期'])

# 创建图表
fig, ax = plt.subplots(figsize=(12, 6))
plt.style.use('dark_background')
plt.rcParams['font.sans-serif'] = ['Microsoft YaHei']
plt.rcParams['axes.unicode_minus'] = False

plt.plot(df['日期'], df['最新值'], label='最新值')

# 设置图表标题和轴标签
plt.title('最新值趋势图')
plt.xlabel('日期')
plt.ylabel('最新值')

# 设置日期格式在x轴上的显示
plt.gca().xaxis.set_major_formatter(mdates.DateFormatter('%Y-%m-%d'))
plt.gca().xaxis.set_major_locator(mdates.AutoDateLocator())

# 自动格式化日期，使其倾斜以提高可读性
plt.gcf().autofmt_xdate()

# 显示图例
legend = plt.legend(frameon=False)
plt.setp(legend.get_title(), color='#808080')
for text in legend.get_texts():
    text.set_color("#808080")
plt.title('农产品批发价格总指数', color='#808080')
plt.xlabel('日期', color='#808080')
plt.ylabel('值', color='#808080')
plt.xticks(rotation=45, color='#808080')
plt.tick_params(colors='#666')
plt.grid(color='#333', linestyle='--')

# 显示网格
plt.grid(visible=True, linestyle='--', linewidth=0.5, alpha=0.7)
ax.set_facecolor('black')
# 保存图表
plt.savefig(output_file, bbox_inches='tight')


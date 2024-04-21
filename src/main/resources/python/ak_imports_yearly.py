import akshare as ak
import matplotlib.pyplot as plt
import pandas as pd
import mplfinance as mpf
import argparse

parser = argparse.ArgumentParser(description='akshare: ak_unemployment [失业率]')
parser.add_argument('--output_file', '-o', type=str, required=True, help="输出图片文件的绝对路径")
args = parser.parse_args()
output_file = args.output_file

df = ak.macro_china_imports_yoy()

# 将日期字符串转换为日期类型
df['日期'] = pd.to_datetime(df['日期'])

# 创建图表
plt.figure(figsize=(12, 6))
plt.style.use('dark_background')

# 绘制线条
plt.plot(df['日期'], df['今值'], label='今值', color='gray', marker='o')
plt.plot(df['日期'], df['预测值'], label='预测值', color='orange', linestyle='--', marker='x')
plt.plot(df['日期'], df['前值'], label='前值', color='pink', linestyle='-.', marker='+')

plt.rcParams['font.sans-serif'] = ['Microsoft YaHei']
plt.rcParams['axes.unicode_minus'] = False
# 创建图例
legend = plt.legend(frameon=False)
plt.setp(legend.get_title(), color='#808080')
for text in legend.get_texts():
    text.set_color("#808080")

# 设置标题和轴标签
plt.title('中国进口年率（美元计算）', color='#808080')
plt.xlabel('日期', color='#808080')
plt.ylabel('值', color='#808080')
plt.tick_params(colors='#666')

# 设置日期格式
plt.gcf().autofmt_xdate()
ax = plt.gca()
ax.spines['top'].set_visible(False)
ax.spines['right'].set_visible(False)
ax.spines['bottom'].set_visible(False)
ax.spines['left'].set_visible(False)

# 网格线
plt.grid(visible=True, color='gray', linestyle='--', linewidth=0.5, alpha=0.5)

# 保存图表
plt.savefig(output_file, bbox_inches='tight')
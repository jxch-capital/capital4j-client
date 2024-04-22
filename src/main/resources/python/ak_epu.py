import akshare as ak
import matplotlib.pyplot as plt
import pandas as pd
import mplfinance as mpf
import argparse
from functools import reduce
import numpy as np

parser = argparse.ArgumentParser(description='akshare: ak_epu [政策不确定性指数]')
parser.add_argument('--output_file', '-o', type=str, required=True, help="输出图片文件的绝对路径")
args = parser.parse_args()
output_file = args.output_file
# output_file = "out_test/ak_epu.png"

def article_epu_index(symbol="China", policy='China_Policy_Index', date_col=None):
    if date_col is None:
        date_col = ['year', 'month']

    df = ak.article_epu_index(symbol=symbol)
    df[symbol] = df[policy]
    df.drop([policy], axis=1, inplace=True)
    df['date'] = pd.to_datetime(df[date_col].assign(DAY=1))
    df = df.set_index('date')
    df.drop(date_col, axis=1, inplace=True)

    return df

dfs = [
    article_epu_index('Global', 'GEPU_ppp', ['Year', 'Month']),
    article_epu_index('USA', 'News_Based_Policy_Uncert_Index', ['Year', 'Month']),
    article_epu_index('Europe', 'European_News_Index', ['Year', 'Month']),
    article_epu_index('Australia', 'Australia_Policy_Index', ['year', 'month']),
    article_epu_index('Canada', 'Canadian_Policy_Index', ['year', 'month']),
    article_epu_index('Germany', 'European_News_Index', ['Year', 'Month']),
    article_epu_index('China', 'China_Policy_Index', ['year', 'month']),
    article_epu_index('Russia', 'Russian_Policy_Index', ['year', 'month']),
    article_epu_index('India', 'Indian_Policy_Index', ['year', 'month']),
    article_epu_index('UK', 'UK_EPU_Index', ['Year', 'Month']),
    article_epu_index('France', 'European_News_Index', ['Year', 'Month']),
]

df = reduce(lambda left, right: pd.merge(left, right, left_index=True, right_index=True, how='outer'), dfs)

query_time = pd.Timestamp('2020-04-22 01:01:01')
df = df[df.index > query_time]


# 创建图表
fig, ax = plt.subplots(figsize=(12, 6))
plt.style.use('dark_background')
plt.rcParams['font.sans-serif'] = ['Microsoft YaHei']
plt.rcParams['axes.unicode_minus'] = False

# 遍历所有列，并绘制它们
for col in df.columns:
    ax.plot(df.index, df[col], label=str(col))  # 使用生成的颜色

# 创建图例
legend = plt.legend(frameon=False)
plt.setp(legend.get_title(), color='#808080')
for text in legend.get_texts():
    text.set_color("#808080")

# 设置标题和轴标签
plt.title('政策不确定性指数', color='#808080')
plt.xlabel('date', color='#808080')
plt.ylabel('value', color='#808080')
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
ax.set_facecolor('black')
# 保存图表
plt.savefig(output_file, bbox_inches='tight')

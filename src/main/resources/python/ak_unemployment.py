import akshare as ak
import matplotlib.pyplot as plt
import pandas as pd
import mplfinance as mpf
import argparse

parser = argparse.ArgumentParser(description='akshare: ak_unemployment [失业率]')
parser.add_argument('--output_file', '-o', type=str, required=True, help="输出图片文件的绝对路径")
args = parser.parse_args()
output_file = args.output_file
# output_file = "out_test/ak_unemployment.png"

macro_china_urban_unemployment_df = ak.macro_china_urban_unemployment()

df_filtered = macro_china_urban_unemployment_df[
    (macro_china_urban_unemployment_df['item'].str.endswith('失业率')) &
    (macro_china_urban_unemployment_df['value'] != 0)
    ]

df_filtered.loc[:, 'date'] = pd.to_datetime(df_filtered['date'], format='%Y%m')

# 切换到暗色背景
plt.style.use('dark_background')
plt.rcParams['font.sans-serif'] = ['Microsoft YaHei']
plt.rcParams['axes.unicode_minus'] = False
plt.figure(figsize=(12, 8))

colors = ['orange', 'green', 'blue', 'cyan', 'magenta', 'yellow', 'gray']

for (item, group), color in zip(df_filtered.groupby('item'), colors):
    plt.plot(group['date'], group['value'], label=item, color=color)

legend = plt.legend(frameon=False)
plt.setp(legend.get_title(), color='#808080')
for text in legend.get_texts():
    text.set_color("#808080")
plt.title('中国城镇各项失业率走势', color='#808080')
plt.xlabel('日期', color='#808080')
plt.ylabel('失业率', color='#808080')
plt.xticks(rotation=45, color='#808080')
plt.tick_params(colors='#666')
plt.grid(color='#333', linestyle='--')

ax = plt.gca()
ax.spines['top'].set_visible(False)
ax.spines['right'].set_visible(False)
ax.spines['bottom'].set_visible(False)
ax.spines['left'].set_visible(False)

plt.savefig(output_file, bbox_inches='tight')


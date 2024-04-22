import akshare as ak
import argparse
import matplotlib.pyplot as plt

parser = argparse.ArgumentParser(description='akshare: ak_tax [税收]')
parser.add_argument('--output_file', '-o', type=str, required=True, help="输出图片文件的绝对路径")
args = parser.parse_args()
output_file = args.output_file
# output_file = "out_test/ak_tax.png"


df = ak.macro_china_national_tax_receipts()

df['年份'] = df['季度'].str.extract(r'(\d{4})年').astype(int)
df['季度标记'] = df['季度'].str.extract(r'第([^季]+季度)').astype(str)

# 创建图表
fig, ax = plt.subplots(figsize=(12, 6))
plt.style.use('dark_background')
plt.rcParams['font.sans-serif'] = ['Microsoft YaHei']
plt.rcParams['axes.unicode_minus'] = False

for quarter in sorted(df['季度标记'].unique()):
    filtered_df = df[df['季度标记'] == quarter]
    ax.plot(filtered_df['年份'], filtered_df['税收收入合计'], label=quarter, marker='o')

# 创建图例
legend = plt.legend(frameon=False)
plt.setp(legend.get_title(), color='#808080')
for text in legend.get_texts():
    text.set_color("#808080")

# 设置标题和轴标签
plt.title('税收收入合计', color='#808080')
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

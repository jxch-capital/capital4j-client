import argparse
import mplfinance as mpf
import pandas as pd

# parser = argparse.ArgumentParser(description='绘制K线图')
# parser.add_argument('--input_csv_file', '-i', type=str, required=True, help="输入股票数据csv文件的绝对路径")
# parser.add_argument('--output_file', '-o', type=str, required=True, help="输出图片文件的绝对路径")
#
# args = parser.parse_args()
# input_file = args.input_csv_file
# output_file = args.output_file

input_file = r"G:\data\development\capital4j-client\tmp\sh.600000-52be5650-1def-4ab3-9520-1baec2cad53a.csv"
output_file = r"G:\data\development\capital4j-client\tmp\sh.600000-52be5650-1def-4ab3-9520-1baec2cad53a.png"

def draw_k_line_chart(data, image_path, width=10, height=6, dpi=100):
    mpf_style = mpf.make_mpf_style(base_mpf_style='nightclouds', base_mpl_style='dark_background',rc={'figure.facecolor': 'black', 'axes.facecolor': 'black'})
    fig, ax = mpf.plot(data,type='candle',style=mpf_style,returnfig=True,volume=True,figsize=(width, height))
    fig.savefig(image_path, dpi=dpi, bbox_inches="tight")
    print(f"图片已保存至{image_path}")

df = pd.read_csv(input_file)
df['time'] = pd.to_datetime(df['time'])
df.set_index('time', inplace=True)
draw_k_line_chart(df, output_file, width=10, height=6, dpi=120)

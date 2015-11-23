package ml.knn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 用来产生原始数据集合，以方便测试聚类算法
 * @author yangchuan
 *
 */
public class GenOriData {
	
	public static void main(String[] args) {
		//用于产生原始数据集合
		String oriPath="C:/Users/hp/git/JavaML/JavaML/file/knnOri.txt";
		writeDataSet(oriPath,genDataLine(true, 100, 50));
		System.out.println(getOriKNNData(oriPath));
		
		//用于产生待测分类数据集合
		String testPath="C:/Users/hp/git/JavaML/JavaML/file/knnTest.txt";
		writeDataSet(testPath,genDataLine(false, 5, 50));
		System.out.println(getOriKNNData(testPath));
	}
	/**
	 * 用来读取文件中的数据，构造原始数据
	 * @param source
	 * @return
	 */
	public static List<Point> getOriKNNData(String source){
		List<Point> players=new ArrayList<Point>();
		List<String> listStr=readFile(source);
		for(String str:listStr){
			if(str!=null){
				String[] playersinfo=str.split(",");
				//如果对文件中每行的数据切割后的字符串数组长度为2，代表生成的是待测数据
				if(playersinfo.length==2){
					Point player=new Point(Double.parseDouble(playersinfo[0]),Double.parseDouble(playersinfo[1]));
					players.add(player);
				}
				//如果对文件中每行的数据切割后的字符串数组长度为2，代表生成的是待测数据
				if(playersinfo.length==3){
					Point player=new Point(Double.parseDouble(playersinfo[0]),Double.parseDouble(playersinfo[1]),Integer.parseInt(playersinfo[2]));
					players.add(player);
				}
			}
		}
		return players;
	}
	
	public static List<String> readFile(String source){
		List<String> listStr=new ArrayList<String>();
		FileReader in=null;
		BufferedReader br=null;
		try {
			in=new FileReader(source);
			br=new BufferedReader(in);
			String line=null;
			while ((line=br.readLine())!=null) {
				listStr.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("文件不存在");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("读取文件异常");
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listStr;
	}
	/**
	 * 随机向文件中写入数据集
	 * @param path     文件路径
	 * @param dataList 数据集
	 */
	public static void writeDataSet(String path,List<String> dataList){
		if(dataList==null||dataList.isEmpty()){
			return ;
		}
		FileWriter out=null;
		BufferedWriter bw=null;
		try {
			out = new FileWriter(path);
			bw=new BufferedWriter(out);
			int i=0;
			while (i<dataList.size()) {
				String line=dataList.get(i);
				System.out.println("line中的数据为"+line);
				bw.write(line);
				bw.newLine();
				i++;
			}
			bw.flush();
		} catch (IOException e) {
			System.out.println("写入文件异常");
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		 
	}
	/**
	 * 
	 * @param num   num表示向文件中写入的行数
	 * @param range 表示随机写入文件的值的最大范围
	 * @param isOri true表示产生的随机数据是样本数据，false表示待分类数据
	 * 
	 */
	
	public static List<String> genDataLine(boolean isOri,int num,double range){
		List<String> dataLines=new ArrayList<String>(num);
		//如果isOri为true则表示要生成样本数据
		if(isOri){
			for(int i=0;i<num;i++){
				int x=(int)(Math.random()*range);
				int y=(int)(Math.random()*range);
				String	line=x+","+y+","+getClusterIdBycoordinate(x, y, range);
				System.out.println(line);
				dataLines.add(line);
			}
		}
		//如果isOri为false则表示要生成待测试分类数据
		else{
			for(int i=0;i<num;i++){
				int x=(int)(Math.random()*range);
				int y=(int)(Math.random()*range);
				String line=x+","+y;
				System.out.println(line);
				dataLines.add(line);
			}
		}
		return dataLines;
	}
	/**
	 * 根据坐标的X Y值，来算出该点属于哪个分类
	 * @param x  该点坐标X值
	 * @param y  该点坐标Y值
	 * @param range  随机产生坐标数值的范围
	 * @return  取range中心点 即(range/2,range/2)分中心点，划分为四个区域1，2，3，4
	 */
	
	public static int getClusterIdBycoordinate(int x,int y,double range){
		double centerX=range/2;
		double centerY=range/2;
		if(x<=centerX&&y<=centerY){
			return 1;
		}
		if(x<=centerX&&y>=centerY){
			return 2;
		}
		if(x>=centerX&&y<=centerY){
			return 3;
		}
		return 4;
	}
}

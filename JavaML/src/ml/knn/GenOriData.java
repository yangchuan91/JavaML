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
 * ��������ԭʼ���ݼ��ϣ��Է�����Ծ����㷨
 * @author yangchuan
 *
 */
public class GenOriData {
	
	public static void main(String[] args) {
		//���ڲ���ԭʼ���ݼ���
		String oriPath="C:/Users/hp/git/JavaML/JavaML/file/knnOri.txt";
		writeDataSet(oriPath,genDataLine(true, 100, 50));
		System.out.println(getOriKNNData(oriPath));
		
		//���ڲ�������������ݼ���
		String testPath="C:/Users/hp/git/JavaML/JavaML/file/knnTest.txt";
		writeDataSet(testPath,genDataLine(false, 5, 50));
		System.out.println(getOriKNNData(testPath));
	}
	/**
	 * ������ȡ�ļ��е����ݣ�����ԭʼ����
	 * @param source
	 * @return
	 */
	public static List<Point> getOriKNNData(String source){
		List<Point> players=new ArrayList<Point>();
		List<String> listStr=readFile(source);
		for(String str:listStr){
			if(str!=null){
				String[] playersinfo=str.split(",");
				//������ļ���ÿ�е������и����ַ������鳤��Ϊ2���������ɵ��Ǵ�������
				if(playersinfo.length==2){
					Point player=new Point(Double.parseDouble(playersinfo[0]),Double.parseDouble(playersinfo[1]));
					players.add(player);
				}
				//������ļ���ÿ�е������и����ַ������鳤��Ϊ2���������ɵ��Ǵ�������
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
			System.out.println("�ļ�������");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("��ȡ�ļ��쳣");
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
	 * ������ļ���д�����ݼ�
	 * @param path     �ļ�·��
	 * @param dataList ���ݼ�
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
				System.out.println("line�е�����Ϊ"+line);
				bw.write(line);
				bw.newLine();
				i++;
			}
			bw.flush();
		} catch (IOException e) {
			System.out.println("д���ļ��쳣");
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
	 * @param num   num��ʾ���ļ���д�������
	 * @param range ��ʾ���д���ļ���ֵ�����Χ
	 * @param isOri true��ʾ����������������������ݣ�false��ʾ����������
	 * 
	 */
	
	public static List<String> genDataLine(boolean isOri,int num,double range){
		List<String> dataLines=new ArrayList<String>(num);
		//���isOriΪtrue���ʾҪ������������
		if(isOri){
			for(int i=0;i<num;i++){
				int x=(int)(Math.random()*range);
				int y=(int)(Math.random()*range);
				String	line=x+","+y+","+getClusterIdBycoordinate(x, y, range);
				System.out.println(line);
				dataLines.add(line);
			}
		}
		//���isOriΪfalse���ʾҪ���ɴ����Է�������
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
	 * ���������X Yֵ��������õ������ĸ�����
	 * @param x  �õ�����Xֵ
	 * @param y  �õ�����Yֵ
	 * @param range  �������������ֵ�ķ�Χ
	 * @return  ȡrange���ĵ� ��(range/2,range/2)�����ĵ㣬����Ϊ�ĸ�����1��2��3��4
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

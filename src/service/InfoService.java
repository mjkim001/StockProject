package service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import dao.InfoDao;
import dao.MemberDao;
import dao.StockDao;
import util.ScanUtil;
import util.View;

public class InfoService {
	
private InfoService(){}

	private static String Samsung = "https://finance.naver.com/item/main.naver?code=005930";
	private static String LGEnerge = "https://finance.naver.com/item/main.naver?code=373220";
	private static String SkHynix = "https://finance.naver.com/item/main.naver?code=000660";
	private static String SamsungWo = "https://finance.naver.com/item/main.naver?code=005935";
	private static String Naver = "https://finance.naver.com/item/main.naver?code=035420";
	private static String SamsungBio = "https://finance.naver.com/item/main.naver?code=207940";
	private static String LGChemical = "https://finance.naver.com/item/main.naver?code=051910";
	private static String HyunDae = "https://finance.naver.com/item/main.naver?code=005380";
	private static String SamsungSDI = "https://finance.naver.com/item/main.naver?code=006400";
	private static String Kakao = "https://finance.naver.com/item/main.naver?code=035720";
	private static String SeltHealth = "https://finance.naver.com/item/main.naver?code=091990";
	private static String EchoPro = "https://finance.naver.com/item/main.naver?code=247540";
	private static String PerAbis = "https://finance.naver.com/item/main.naver?code=263750";
	private static String LNF = "https://finance.naver.com/item/main.naver?code=066970";
	private static String KakaoGame = "https://finance.naver.com/item/main.naver?code=293490";
	private static String HLB = "https://finance.naver.com/item/main.naver?code=028300";
	private static String Wemade = "https://finance.naver.com/item/main.naver?code=112040";
	private static String SeltDrug = "https://finance.naver.com/item/main.naver?code=068760";
	private static String CheonBo = "https://finance.naver.com/item/main.naver?code=278280";
	private static String CJENM = "https://finance.naver.com/item/main.naver?code=035760";

	private static String[] stockArr = {Samsung, LGEnerge, SkHynix, SamsungWo, Naver, SamsungBio, LGChemical, HyunDae, SamsungSDI, Kakao, SeltHealth, EchoPro
			,PerAbis, LNF, KakaoGame, HLB, Wemade, SeltDrug, CheonBo, CJENM};
	
	private static InfoService instance; 
	private InfoDao infoDao = InfoDao.getInstance();
	
	public static InfoService getInstance() { 
		if(instance == null) {
			// ????????? ????????? ?????? ??????
			instance = new InfoService();
		}
		return instance;
	}
	

	public int viewUserInfo() {
		MemberService.loginMember =  MemberDao.getInstance().getMember(MemberService.loginMember.get("MEM_ID"));
		
//		for(String stock : stockArr) {			
//			StockService.getInstance().stockUpdate(stock);
//		}
		
		List<Map<String, Object>> myInfo = infoDao.viewInfo(MemberService.loginMember.get("MEM_ID"));

		if(myInfo.size() != 0) {
			Map<String, Object> totalAssetMap = MemberDao.getInstance().getTotalAsset();
			int totalAsset = Integer.parseInt(String.valueOf(totalAssetMap.get("ASSET")));
			MemberDao.getInstance().updateAsset(totalAsset);
			MemberService.loginMember =  MemberDao.getInstance().getMember(MemberService.loginMember.get("MEM_ID"));
		}
		
		int myMoney = Integer.parseInt(String.valueOf(MemberService.loginMember.get("MY_MONEY")));
		int totalAsset = Integer.parseInt(String.valueOf(MemberService.loginMember.get("TOTAL_ASSET")));
		
		
		int index = 1;
		System.out.println("=================================================================================================================================");
		System.out.println("????????????\t ????????????:" + NumberFormat.getInstance().format(myMoney) + "\t ?????????:" + NumberFormat.getInstance().format(totalAsset));
		System.out.printf("  %10s\t     ????????????\t ????????????\t    ????????????\t ?????????","?????????");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
		for (Map<String, Object> f : myInfo) {
			int avgPrice = Integer.parseInt(String.valueOf(f.get("AVG_PRICE")));
			int curPrice = Integer.parseInt(String.valueOf(f.get("CUR_PRICE")));
			System.out.printf("%-3d", index++);
			System.out.printf("%-10s\t%10s  %10s %10s %10s",f.get("STOCK_NAME"), NumberFormat.getInstance().format(avgPrice),
					NumberFormat.getInstance().format(curPrice),f.get("MY_QTY"),f.get("PROFIT"));
			System.out.println();
		}
		System.out.println("=================================================================================================================================");
		
		System.out.print("1.[????????????????????????] 0.[????????????] > ");
		int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1: StockService.getInstance().viewStockInfo("INFORMATION"); break;
		case 0: return View.MAIN;
		}
		return View.MAIN;
	}
	
}

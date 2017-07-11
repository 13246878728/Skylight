public class ATMBO{
	public String code = "1001";
	public int passward = 111;
	public double money = 1000.00;
	
	public int cs = 0;    //当前账户登录错误次数
	public int doLogin(String code1, int passward1){
		if(code1.equals(code) && passward1 == passward){
			return-1;
		}else{
			cs++;
			return cs;
		}
	}
	
	
	public double doChaxun(){
		
		return money;
		
		
	}
	
	public int doCunkuan(double i){
		
		
		if (i%100==0){
			
			money=money+i;
			System.out.println(money);
			return-2;
		}else
			return-3;
	}
	public int doQukuan(double p){
		if(p%100==0){
			if(p<=money){
				money=money-p;
				return-1;
			}else{
				return-2;
			}
			
			
		}else{
			return-3;
			
		}
		
	}
	public int doXiugai(int mima1,int mima2,int mima3){
		if(mima1==passward){
			if(mima2 == mima3){
				passward=mima2;
			    return-1;
			}else{
				return-2;
			}
		}else{
			return-3;
		}
	}

}
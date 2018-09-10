package main;

import java.util.Scanner;

public class SOM {
public static int brojac=0;
	public static int brojGradova; // broj gradova
	public int	brNerona;  // broj neurona
	public static final double	COUNTRY = 1.00;
	public static final double	NEAR = 0.05; //0.05
	
	public Grad grad[]; //niz gradova
	public Neuron neuroni[]; // niz neurona
	
	public static double r[][]; // matrica
	
	public double theta, phi, momentum; 
	
	public void kohonenInit(){
		if(brojac==0) {
			
	    Scanner s = new Scanner(System.in);
		System.out.println("unesite broj gradova:");
		 brojGradova = s.nextInt();
		 brojac++;
		 }
	
		theta =  0.5; //0.3;
	   phi   =0.5; // 0.7; 
	   momentum =0.995;// 0.9987; 

		
		brNerona = brojGradova*2; // broj neurona je duplo veci
		
    
	  grad = new Grad[brojGradova];
	  for(int i = 0; i<brojGradova; i++) grad[i] = new Grad(Math.random()*COUNTRY, Math.random()*COUNTRY);
	  
	  double alpha = 0.0;      
	  neuroni= new Neuron[brNerona];
	  for(int i = 0; i<brNerona; i++){
	    neuroni[i] = new Neuron(0.5+0.5*Math.cos(alpha),0.5+0.5*Math.sin(alpha)); 
	    alpha += Math.PI *2.0 / (double)(brNerona);
	  }
	  
	  r = new double[brNerona][brNerona];
	  
	  KreirajMaricuR(theta);        
	
		//	counter = 0;
		
		}

	private void KreirajMaricuR(double theta2) {
		for(int i=0; i<brNerona; i++){
	      	r[i][i]= 1.0;
	      	for(int j=i+1; j<brNerona; j++){
	      		r[i][j] = Math.exp( -1.0 * ( Math.pow(neuroni[i].rastojanjeIzmedjuNeurona(neuroni[j]), 2) )/(2.0*theta2*theta2));
	      		r[j][i] = r[i][j];
	     			
	      	}
	     
	      }
		
	}
	
	private int randomGrad(){
        int gradIndex = (int) (Math.random() *brojGradova);
        
        return gradIndex;
    }
	
	
	private int pronadjiNajbliziNeuronMetodomSA(double x,double y) {
		SimuliranoKaljenje sa = new SimuliranoKaljenje(neuroni);
        return sa.anneal(x, y);
	}
	
	public void Trening() {
		double x1,y1;
		int j;
		int index = randomGrad();
		
		x1 =  (grad[index].getX()+(Math.random()*NEAR)-NEAR/2); //izracuna najblizu vrednost
        y1 =  (grad[index].getY()+(Math.random()*NEAR)-NEAR/2);
        grad[index].setChoose(1); //postavi da je grad izabran
		
        j = pronadjiNajbliziNeuronMetodomSA(x1, y1);
       // j=pronadjiNajblizi(x1,y1);
        neuroni[j].update++;
        
        promeniTezineNeuronima(x1,y1,j);
        
     // DECREASE LEARNING PARAMETERS
        phi *= momentum;
        theta *= momentum;
        
        KreirajMaricuR(theta); // ponovo kreiraj matricu r
        
	}

	private void promeniTezineNeuronima(double x1,double y1,int j) {
		 for(int i=0; i<brNerona;i++){
         	neuroni[i].wx += (phi * r[i][j] * (x1 - neuroni[i].wx));
         	neuroni[i].wy += (phi * r[i][j] * (y1 - neuroni[i].wy));
         }
		
	}

	private int pronadjiNajblizi(double x1,double y2) {
		
		double  min = Double.MAX_VALUE;
		int j;
         j = -1;
         for(int i=0; i<brNerona;i++){
         	double d = Math.pow((x1 - neuroni[i].wx), 2) + Math.pow((y2 - neuroni[i].wy), 2);
         	
         	if(d < min){
         		min = d;
         		j = i;
         	}
         }
         return j;
	}
	
	public double UkupnaDistanca(){
		
        double total = 0;
        
        for(int i = 0; neuroni.length-1 > i; i++){
            total += neuroni[i].TezinskoRastojanje(neuroni[i+1]);
        }
        
        total += neuroni[0].TezinskoRastojanje(neuroni[neuroni.length-1]);
        
        return total;
    }
	
	
}

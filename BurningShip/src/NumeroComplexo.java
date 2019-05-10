public class NumeroComplexo {
	private double real;
	private double imaginario;

	NumeroComplexo(double real, double imaginario) {
		this.real = real;
		this.imaginario = imaginario;
	}

	public void multiplicacao(NumeroComplexo x) {
		double r = this.real * x.real - this.imaginario * x.imaginario;
		double i = this.real * x.imaginario + this.imaginario * x.real;
		this.real = r;
		this.imaginario = i;
	}
	public void potencia(int x) {
		NumeroComplexo y = this;
		for (int i = 1;i < x;i++) {
			this.multiplicacao(this);
		}
	}
	public void soma(NumeroComplexo x) {
		this.real += x.real;
		this.imaginario += x.imaginario;
	}
	public double mod() {
		return this.real * this.real + this.imaginario * this.imaginario;
	}
	public void abs() {
		this.real = Math.abs(this.real);
		this.imaginario = Math.abs(this.imaginario);
	}
}

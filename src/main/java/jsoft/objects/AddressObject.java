package jsoft.objects;

public class AddressObject {
	   private int id;
	   private String addressDetail;
	   private String wards;
	    private String districts;
	    private String provinces;
	    private boolean isDefault;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getAddressDetail() {
			return addressDetail;
		}
		public void setAddressDetail(String addressDetail) {
			this.addressDetail = addressDetail;
		}
		public String getWards() {
			return wards;
		}
		public void setWards(String wards) {
			this.wards = wards;
		}
		public String getDistricts() {
			return districts;
		}
		public void setDistricts(String districts) {
			this.districts = districts;
		}
		public String getProvinces() {
			return provinces;
		}
		public void setProvinces(String provinces) {
			this.provinces = provinces;
		}
		public boolean isDefault() {
			return isDefault;
		}
		public void setDefault(boolean isDefault) {
			this.isDefault = isDefault;
		}
		
}

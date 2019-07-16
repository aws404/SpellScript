package aws404.spells;

public enum DataType{
	   STRING(String.class), 
	   DOUBLE(Double.class), 
	   INTEGER(Integer.class),
	   LONG(Long.class),
	   FLOAT(Float.class), 
	   BOOLEAN(Boolean.class);

	   

	
		private Class<Object> type;
	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private DataType(Class type) {
	        this.type = type;
		}	

		public Class<Object> typeClass() {
		   return type;
		}
		
	   
}

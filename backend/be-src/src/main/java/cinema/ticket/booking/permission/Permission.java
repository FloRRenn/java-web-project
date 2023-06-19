package cinema.ticket.booking.permission;

public enum Permission {
	
	ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete");
	
	private String permission;
	
	Permission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return this.permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
    
}

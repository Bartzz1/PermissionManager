package net.bartzz.pexmanager.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.scheduler.BukkitRunnable;

import net.bartzz.pexmanager.Main;

public class MCDatabase {
	
	private String			host;
	private String			username;
	private String			password;
	private String			database;
	private int				port;
	private Connection		conn;
	private long			time;
	private Executor		executor;
	private AtomicInteger	ai;

	public MCDatabase(String host, int port, String username, String password, String database) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.database = database;
		this.executor = Executors.newSingleThreadExecutor();
		this.time = System.currentTimeMillis();
		this.ai = new AtomicInteger();
		this.connect();
		new BukkitRunnable() {
			public void run() {
				if (System.currentTimeMillis() - MCDatabase.this.time > (long) 30 * 1000) {
					MCDatabase.this.update(false, "DO 1");
				}

			}
		}.runTaskTimer(Main.getInstance(), 20 * 30L, 20 * 30L);
	}

	public Connection getConnection() {
		return this.conn;
	}
	
	public void createTables() throws SQLException {
		String query = ("create table if not exists coins_players (uuid varchar(100) not null, nick varchar(50) not null, coins int not null)");
		this.getConnection().createStatement().executeUpdate(query);
	}

	public boolean isConnected() {
		try {
			return !this.conn.isClosed() || this.conn == null;
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			return false;
		}
	}

	public boolean connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
			Main.getInstance().getLogger().info("Connected to the MySQL server.");
			return true;
		} catch (ClassNotFoundException exception) {
			Main.getInstance().getLogger().warning("JDBC Driver not found.");
			Main.getInstance().getLogger().warning("Error: " + exception.getMessage());
			exception.printStackTrace();
		} catch (SQLException exception) {
			Main.getInstance().getLogger().warning("Cannot connect to the MySQL server.");
			Main.getInstance().getLogger().warning("Error: " + exception.getMessage());
			exception.printStackTrace();
		}
		return false;
	}

	public void disconnect() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException exception) {
				Main.getInstance().getLogger().warning("Cannot close the connection to the MySQL server.");
				Main.getInstance().getLogger().warning("Error: " + exception.getMessage());
				exception.printStackTrace();
			}
		}
	}

	public void reconnect() {
		this.connect();
	}

	public void update(boolean now, final String update) {
		this.time = System.currentTimeMillis();
		Runnable r = new Runnable() {
			public void run() {
				try {
					conn.createStatement().executeUpdate(update);
				} catch (SQLException exception) {
					Main.getInstance().getLogger().warning("An error occured with given query '" + update + "'.");
					Main.getInstance().getLogger().warning("Error: " + exception.getMessage());
					exception.printStackTrace();
				}
			}
		};

		if (now) {
			r.run();
		} else {
			this.executor.execute(r);
		}

	}

	public ResultSet update(String update) {
		try {
			Statement e = this.conn.createStatement();
			e.executeUpdate(update, 1);
			ResultSet rs = e.getGeneratedKeys();
			if (rs.next()) {
				return rs;
			}
		} catch (SQLException exception) {
			Main.getInstance().getLogger().warning("An error occured with given query '" + update + "'.");
			Main.getInstance().getLogger().warning("Error: " + exception.getMessage());
			exception.printStackTrace();
		}
		return null;
	}

	public ResultSet query(String query) {
		try {
			return this.conn.createStatement().executeQuery(query);
		} catch (SQLException exception) {
			Main.getInstance().getLogger().warning("An error occured with given query '" + query + "'.");
			Main.getInstance().getLogger().warning("Error: " + exception.getMessage());
			exception.printStackTrace();
			return null;
		}
	}

	public void query(final String query, final Callback cb) {
		new Thread(new Runnable() {
			public void run() {
				try {
					ResultSet e = conn.createStatement().executeQuery(query);
					cb.done(e);
				} catch (SQLException exception) {
					Main.getInstance().getLogger().warning("An error occured with given query '" + query + "'.");
					Main.getInstance().getLogger().warning("Error: " + exception.getMessage());
					cb.error(exception);
					exception.printStackTrace();
				}
			}
		}, "MySQL Thread #" + this.ai.getAndIncrement()).start();
	}

	private interface Callback {

		Object done(Object object);

		void error(Throwable throwable);
	}
}

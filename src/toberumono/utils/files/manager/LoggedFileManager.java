package toberumono.utils.files.manager;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An implementation of {@link AbstractFileManager} that logs additions, changes, and removals by sending appropriate
 * {@link Level#INFO} messages to a {@link Logger}.<br>
 * This is mainly useful as a placeholder when implementing more complex programs.
 * 
 * @author Toberumono
 */
public class LoggedFileManager extends AbstractFileManager {
	private final Logger logger;
	
	/**
	 * Creates a {@link LoggedFileManager} on the default {@link FileSystem} with the {@link Logger} returned by
	 * {@code Logger.getLogger(LoggedFileManager.class.getName())}.
	 * 
	 * @throws IOException
	 *             if a {@link WatchService} could not be created on the default {@link FileSystem}
	 */
	public LoggedFileManager() throws IOException {
		this(Logger.getLogger(LoggedFileManager.class.getName()));
	}
	
	/**
	 * Creates a {@link LoggedFileManager} on the given {@link FileSystem} with the {@link Logger} returned by
	 * {@code Logger.getLogger(LoggedFileManager.class.getName())}.
	 * 
	 * @param fileSystem
	 *            the {@link FileSystem} on which the {@link LoggedFileManager} will manage files
	 * @throws IOException
	 *             if a {@link WatchService} could not be created on the default {@link FileSystem}
	 */
	public LoggedFileManager(FileSystem fileSystem) throws IOException {
		this(Logger.getLogger(LoggedFileManager.class.getName()), fileSystem);
	}
	
	/**
	 * Creates a {@link LoggedFileManager} on the default {@link FileSystem} with the given {@link Logger}.
	 * 
	 * @param logger
	 *            the {@link Logger} to use
	 * @throws IOException
	 *             if a {@link WatchService} could not be created on the default {@link FileSystem}
	 */
	public LoggedFileManager(Logger logger) throws IOException {
		super();
		this.logger = logger;
	}
	
	/**
	 * Creates a {@link LoggedFileManager} on the given {@link FileSystem} with the given {@link Logger}.
	 * 
	 * @param logger
	 *            the {@link Logger} to use
	 * @param fileSystem
	 *            the {@link FileSystem} on which the {@link LoggedFileManager} will manage files
	 * @throws IOException
	 *             if a {@link WatchService} could not be created on the default {@link FileSystem}
	 */
	public LoggedFileManager(Logger logger, FileSystem fileSystem) throws IOException {
		super(fileSystem);
		this.logger = logger;
	}
	
	@Override
	protected void onAddFile(Path path) throws IOException {
		logger.info("Added file: " + path.toString());
	}
	
	@Override
	protected void onAddDirectory(Path path) throws IOException {
		logger.info("Added directory: " + path.toString());
	}
	
	@Override
	protected void onChangeFile(Path path) throws IOException {
		logger.info("Changed file: " + path.toString());
	}
	
	@Override
	protected void onChangeDirectory(Path path) throws IOException {
		logger.info("Changed directory: " + path.toString());
	}
	
	@Override
	protected void onRemoveFile(Path path) throws IOException {
		logger.info("Removed file: " + path.toString());
	}
	
	@Override
	protected void onRemoveDirectory(Path path) throws IOException {
		logger.info("Removed directory: " + path.toString());
	}
	
	@Override
	protected void handleException(Path path, Throwable t) {
		if (t != null)
			logger.log(Level.SEVERE, "Error while processing the item at " + path, t);
		else
			logger.log(Level.SEVERE, "Error while processing the item at " + path);
	}
	
	/**
	 * Testing method
	 * 
	 * @param args
	 *            ignored
	 * @throws IOException
	 *             because handling these is pointless here
	 */
	public static void main(String[] args) throws IOException {
		FileManager fm = new LoggedFileManager();
		long time = System.nanoTime();
		fm.add(Paths.get("/Users/joshualipstone/Downloads"));
		fm.add(Paths.get("/Users/joshualipstone/Downloads/Compressed/"));
		System.out.println((System.nanoTime() - time) / 1000000);
		Scanner delay = new Scanner(System.in);
		delay.nextLine();
		delay.close();
		fm.close();
	}
}

package com.massivecraft.mcore4.util;

import java.io.File;

import com.massivecraft.mcore4.MPlugin;

public class LibLoader
{	
	MPlugin p;
	public LibLoader(MPlugin p)
	{
		this.p = p;
	}
	
	public boolean require(String filename, String url)
	{
		if ( ! include(filename, url))
		{
			p.log("Failed to load the required library "+filename);
			p.suicide();
			return false;
		}
		return true;
	}
	
	public boolean include (String filename, String url)
	{
		File file = getFile(filename);
		if ( ! file.exists())
		{
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) parent.mkdirs();
			
			p.log("Downloading library "+filename);
			if ( ! DiscUtil.downloadUrl(url, file))
			{
				p.log("Failed to download "+filename);
				return false;
			}
		}
		
		return ClassLoadHack.load(file);
	}
	
	private static File getFile(String filename)
	{
		return new File("./lib/"+filename);
	}
}
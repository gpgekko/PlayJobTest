package jobs;

import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.IO;
import play.vfs.VirtualFile;

import static play.Play.confs;

/**
 * Read the build file if there is any.
 * <p/>
 * Project ViewerPro Web - Core<br/>
 * ReadJob.java created 25-mrt-2014
 * <p/>
 * Copyright &copy 2014 SemLab
 *
 * @param <V>
 *
 * @author <a href="mailto:pot@semlab.nl">Guylian Pot</a>
 * @version $Revision: 1.2 $, $Date: 2014/03/26 08:48:08 $
 */
@OnApplicationStart(async=true)
public class ReadJob<V> extends Job<V>
{
	@Override
	public void doJob() throws Exception
	{
        if(Play.mode.isDev())
		{
			try
			{
				VirtualFile build = VirtualFile.open(Play.applicationPath).child("distbuild.number");
				if(build.exists() && !Play.confs.contains(build))
				{
					confs.add(build);
					Play.configuration.putAll(IO.readUtf8Properties(build.inputstream()));
				}
			}
			catch(Exception e)
			{
				Logger.warn(e, "Caught exception while searching for build number. Aborting and assuming release.");
			}
		}
	}
}
#!/system/bin/sh



sweep2wakeon()
{
	#Delete the line containing the HTC screenshot feature out of the build.prop
	#regardless of the setting and replace it with the disable command.
	#This is a compatibility fix for sweep2wake (avoids taking screenshots while locking the phone)
	if [ -e /system/build.prop ] ; then
		found=$(find /system/build.prop -type f | xargs grep -oh "ro.htc.framework.screencapture = false");
		if [ "$found" != 'ro.htc.framework.screencapture = false' ]; then
			#delete that line completely regardless of the setting
			sed '/ro.htc.framework.screencapture/d' /system/build.prop > /system/tmpfile
			rm /system/build.prop
			mv /system/tmpfile /system/build.prop
			#append the new lines for this option at the bottom
			found=$(find /system/build.prop -type f | xargs grep -oh "ro.htc.framework.screencapture");
			if [ "$found" != 'ro.htc.framework.screencapture' ]; then
				echo "\n" >> /system/build.prop;
				echo "ro.htc.framework.screencapture = false" >> /system/build.prop;
				echo "\n" >> /system/build.prop;
			fi
		fi
	fi
}

sweep2wakeoff()
{
	#Delete the line containing the HTC screenshot feature out of the build.prop
	#regardless of the setting and replace it with the enable command.
	#This is reverts the compatibility fix for sweep2wake
	if [ -e /system/build.prop ] ; then
		found=$(find /system/build.prop -type f | xargs grep -oh "ro.htc.framework.screencapture = true");
		if [ "$found" != 'ro.htc.framework.screencapture = true' ]; then
			#delete that line completely regardless of the setting
			sed '/ro.htc.framework.screencapture/d' /system/build.prop > /system/tmpfile
			rm /system/build.prop
			mv /system/tmpfile /system/build.prop
			#append the new lines for this option at the bottom
			found=$(find /system/build.prop -type f | xargs grep -oh "ro.htc.framework.screencapture");
			if [ "$found" != 'ro.htc.framework.screencapture' ]; then
				echo "\n" >> /system/build.prop;
				echo "ro.htc.framework.screencapture = true" >> /system/build.prop;
				echo "\n" >> /system/build.prop;
			fi
		fi
	fi
}

#get sweep2wake setting
mount -o remount, rw /system


    s2w=$1
    if [ $s2w -eq "0" ] ;
    then
    sweep2wakeoff
    elif [ $s2w -eq "1" ];
     then
    sweep2wakeon
    elif [ $s2w -eq "2" ];
     then
    sweep2wakeon
    fi


#Add s2w to the kernels cmdline.
    s2w_start=$2
    s2w_end=$3
cmdline=$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)
searchString="s2w="
s2w="s2w="$s2w
case $cmdline in
  "$searchString"* | *" $searchString"*)
   	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline | sed -e 's/s2w=[^ ]\+//')>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $s2w>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;  
  *)
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $s2w>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;
esac

cmdline=$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)
searchString="s2w_start="
s2w_start="s2w_start="$s2w_start
case $cmdline in
  "$searchString"* | *" $searchString"*)
   	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline | sed -e 's/s2w_start=[^ ]\+//')>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $s2w_start>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;  
  *)
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $s2w_start>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;
esac

cmdline=$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)
searchString="s2w_end="
s2w_end="s2w_end="$s2w_end
case $cmdline in
  "$searchString"* | *" $searchString"*)
   	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline | sed -e 's/s2w_end=[^ ]\+//')>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $s2w_end>//data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;  
  *)
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $s2w_end>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;
esac

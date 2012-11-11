#!/system/bin/sh

#
#copyright 2011 Chad Goodman - All Rights reserved
#
#modified by pedja for Anthrax Flasher
#

#mpdecision and thermald management
#delete old script
if [ -e /system/etc/init.d/99feature ] ; then
	rm /system/etc/init.d/99feature
fi
#create script
touch /system/etc/init.d/99anthrax

#set permissions
chmod 777 /system/etc/init.d/99anthrax

#create script header
echo "#!/system/bin/sh" > /system/etc/init.d/99anthrax;
echo "" >>/system/etc/init.d/99anthrax;
echo "#Set anthrax features" >>/system/etc/init.d/99anthrax;
echo "" >>/system/etc/init.d/99anthrax;
echo "" >>/system/etc/init.d/99anthrax;

#load scsi-wait driver
echo "# load scsi-wait driver for OTG" >>/system/etc/init.d/99anthrax;
echo "insmod /system/lib/modules/scsi_wait_scan.ko" >>/system/etc/init.d/99anthrax
echo "" >>/system/etc/init.d/99anthrax;
#end scsi-wait

#get VSYNC setting and create init.d script
val=$1
if [ $val -eq "0" ]; then
	echo "# set VSYNC off" >>/system/etc/init.d/99anthrax;
	echo "mount -t debugfs debugfs /sys/kernel/debug" >>/system/etc/init.d/99anthrax
	echo "echo 0 >/sys/kernel/debug/msm_fb/0/vsync_enable" >>/system/etc/init.d/99anthrax
	echo "echo 0 >/sys/kernel/debug/msm_fb/0/hw_vsync_mode" >>/system/etc/init.d/99anthrax
	echo "echo 4 >/sys/kernel/debug/msm_fb/0/backbuff" >>/system/etc/init.d/99anthrax
	echo "" >>/system/etc/init.d/99anthrax;
 elif [ $val -eq "1" ]; then   
	echo "# set VSYNC on" >>/system/etc/init.d/99anthrax;
	echo "mount -t debugfs debugfs /sys/kernel/debug" >>/system/etc/init.d/99anthrax
	echo "echo 1 >/sys/kernel/debug/msm_fb/0/vsync_enable" >>/system/etc/init.d/99anthrax
	echo "echo 1 >/sys/kernel/debug/msm_fb/0/hw_vsync_mode" >>/system/etc/init.d/99anthrax
	echo "echo 3 >/sys/kernel/debug/msm_fb/0/backbuff" >>/system/etc/init.d/99anthrax
	echo "" >>/system/etc/init.d/99anthrax;
 fi   

#get MPDECISION setting and create init.d script
val=$2

 if [ $val -eq "1" ]; then
	echo "# mpdecision - kernel support" >>/system/etc/init.d/99anthrax;
	echo "echo 1 >/sys/kernel/msm_mpdecision/conf/enabled" >>/system/etc/init.d/99anthrax
		if [ -e /system/bin/mpdecision ] ; then
		busybox mv /system/bin/mpdecision /system/bin/mpdecision_backup
		fi
	echo "" >>/system/etc/init.d/99anthrax;
  
  elif [ $val -eq "0" ]; then
 
	echo "# mpdecision - rom support" >>/system/etc/init.d/99anthrax;
	echo "echo 0 >/sys/kernel/msm_mpdecision/conf/enabled" >>/system/etc/init.d/99anthrax
	if [ -e /system/bin/mpdecision_backup ] ; then
		busybox mv /system/bin/mpdecision_backup /system/bin/mpdecision
	fi
	echo "" >>/system/etc/init.d/99anthrax;
    
   fi






##Get screen off governor from aroma tmp
val=$(cat /tmp/aroma-data/govoff.prop | cut -d"=" -f2)
case $val in
  1)
	echo "# screen off cpu governor - on demand" >>/system/etc/init.d/99anthrax;
	echo "echo ondemand >/sys/kernel/msm_mpdecision/conf/mpdec_scroff_gov" >>/system/etc/init.d/99anthrax
	echo "" >>/system/etc/init.d/99anthrax;
    ;;
  2)
	echo "# screen off cpu governor - interactive" >>/system/etc/init.d/99anthrax;
	echo "echo interactive >/sys/kernel/msm_mpdecision/conf/mpdec_scroff_gov" >>/system/etc/init.d/99anthrax
	echo "" >>/system/etc/init.d/99anthrax;
    ;;
  3)
	echo "# screen off cpu governor - conservative" >>/system/etc/init.d/99anthrax;
	echo "echo conservative >/sys/kernel/msm_mpdecision/conf/mpdec_scroff_gov" >>/system/etc/init.d/99anthrax
	echo "" >>/system/etc/init.d/99anthrax;
    ;;
  4)
	echo "# screen off cpu governor - userspace" >>/system/etc/init.d/99anthrax;
	echo "echo userspace >/sys/kernel/msm_mpdecision/conf/mpdec_scroff_gov" >>/system/etc/init.d/99anthrax
	echo "" >>/system/etc/init.d/99anthrax;
    ;;
  5)
	echo "# screen off cpu governor - powersave" >>/system/etc/init.d/99anthrax;
	echo "echo powersave >/sys/kernel/msm_mpdecision/conf/mpdec_scroff_gov" >>/system/etc/init.d/99anthrax
	echo "" >>/system/etc/init.d/99anthrax;
    ;;
  6)
	echo "# screen off cpu governor - performance" >>/system/etc/init.d/99anthrax;
	echo "echo performance >/sys/kernel/msm_mpdecision/conf/mpdec_scroff_gov" >>/system/etc/init.d/99anthrax
	echo "" >>/system/etc/init.d/99anthrax;
    ;;
  7)
	echo "# screen off cpu governor - xondemand" >>/system/etc/init.d/99anthrax;
	echo "echo xondemand >/sys/kernel/msm_mpdecision/conf/mpdec_scroff_gov" >>/system/etc/init.d/99anthrax
	echo "" >>/system/etc/init.d/99anthrax;
    ;;
  8)
    gov="lagfree"
	echo "# screen off cpu governor - lagfree" >>/system/etc/init.d/99anthrax;
	echo "echo lagfree >/sys/kernel/msm_mpdecision/conf/mpdec_scroff_gov" >>/system/etc/init.d/99anthrax
	echo "" >>/system/etc/init.d/99anthrax;
    ;;
esac

#set camera mode to sense - AOSP mode is no longer needed for newer builds of CM10 from agrabren
#left this here incase some users flash other roms, or older versions of CM10, they can edit "0" to "1"
echo "echo 0 >/sys/kernel/rom_type/rom_type" >>/system/etc/init.d/99anthrax

#create script footer
echo "" >>/system/etc/init.d/99anthrax;



return $?


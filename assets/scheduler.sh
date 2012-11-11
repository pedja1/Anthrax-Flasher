#!/system/bin/sh

##Get governor 
    scheduler=$1

#Add scheduler to the kernels cmdline.
cmdline=$(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)
searchString="scheduler="
scheduler="scheduler="$scheduler
case $cmdline in
  "$searchString"* | *" $searchString"*)
   	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline | sed -e 's/scheduler=[^ ]\+//')>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $scheduler>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;  
  *)
	echo $(cat /data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline)\ $scheduler>/data/data/rs.pedjaapps.anthrax.flasher/files/temp/boot.img-cmdline
	;;
esac
#end scheduler


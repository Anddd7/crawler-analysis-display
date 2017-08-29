sample
===
* 注释

	select #use("cols")# from search_source_sample where #use("condition")#

cols
===

	badgepay,play,description,pubdate,title,arcrank,review,pic,mid,arcurl,tag,video_review,author,favorites,duration,type,senddate,cateid,id

updateSample
===

	`badgepay`=#badgepay#,`play`=#play#,`description`=#description#,`pubdate`=#pubdate#,`title`=#title#,`arcrank`=#arcrank#,`review`=#review#,`pic`=#pic#,`mid`=#mid#,`arcurl`=#arcurl#,`tag`=#tag#,`video_review`=#videoReview#,`author`=#author#,`favorites`=#favorites#,`duration`=#duration#,`type`=#type#,`senddate`=#senddate#,`cateid`=#cateid#,`id`=#id#

condition
===

	1 = 1  
	@if(!isEmpty(badgepay)){
	 and `badgepay`=#badgepay#
	@}
	@if(!isEmpty(play)){
	 and `play`=#play#
	@}
	@if(!isEmpty(description)){
	 and `description`=#description#
	@}
	@if(!isEmpty(pubdate)){
	 and `pubdate`=#pubdate#
	@}
	@if(!isEmpty(title)){
	 and `title`=#title#
	@}
	@if(!isEmpty(arcrank)){
	 and `arcrank`=#arcrank#
	@}
	@if(!isEmpty(review)){
	 and `review`=#review#
	@}
	@if(!isEmpty(pic)){
	 and `pic`=#pic#
	@}
	@if(!isEmpty(mid)){
	 and `mid`=#mid#
	@}
	@if(!isEmpty(arcurl)){
	 and `arcurl`=#arcurl#
	@}
	@if(!isEmpty(tag)){
	 and `tag`=#tag#
	@}
	@if(!isEmpty(videoReview)){
	 and `video_review`=#videoReview#
	@}
	@if(!isEmpty(author)){
	 and `author`=#author#
	@}
	@if(!isEmpty(favorites)){
	 and `favorites`=#favorites#
	@}
	@if(!isEmpty(duration)){
	 and `duration`=#duration#
	@}
	@if(!isEmpty(type)){
	 and `type`=#type#
	@}
	@if(!isEmpty(senddate)){
	 and `senddate`=#senddate#
	@}
	@if(!isEmpty(cateid)){
	 and `cateid`=#cateid#
	@}
	@if(!isEmpty(id)){
	 and `id`=#id#
	@}
	

<p>Yet Another Protection System or YAPS</p>

<h3 id="w-color-blue-what-is-this-color"><span style="color: #0000ff;">What is this ?</span></h3>
<p>
I like plugins with block protection, where the action of placing a protected block protects that block, and area protection too.
Some plugins do one thing, and others do the other... what if one plugin did both?
What if you want some area, like spawn, protected by area and players also have protection by block place/remove?
Then this is your plugin!
</p>
<p>
Do more with less!
</p>
<p>
Do more - Protect by area or block or both<br />
With Less - This plugin will focus only on that... easy, light and simple... no extra features...
</p>

<h3 id="w-color-blue-features-color"><span style="color: #0000ff;">Features</span></h3>
<ul>
<li>Protection by block</li>
<li>Protection by area</li>
<li>Use an item to find the owner of a protected block</li>
<li>Use a command to check if the targeted block is protected</li>
<li>Mysql/SQlite support</li>
<li>Optimized Mysql Connection System</li>
<li>Many tweaks on how the protection system will work!</li>
</ul>

<h2 id="w-color-red-planned-color"><span style="color: #ff0000;">Planned</span></h2>
<ul>
<li><del>Add limited blocks and regions</del></li>
<li><del>Use item to find owner of protected block</del></li>
<li><del>Command to know if that block is "protected block"</del></li>
<li>Add Flag system to area ( AllowDestroy, BlockCmd, Add more then one owner, etc... )</li>
<li>Add System do player buy / sell / add friend on area</li>
</ul>

<h2 id="w-color-red-requirement-color"><span style="color: #ff0000;">Requirement</span></h2>
<ul>
<li>Java 1.8+</li>
<li>Vault</li>
</ul>

<h3 id="w-color-blue-commands-color"><span style="color: #0000ff;">Commands</span></h3>
<p>All commands don't work on console.</p>

<p><strong>By Area<br /> <br /></strong></p>
<ul>
<li>/yaps &lt;w or wand&gt; - get the wand to set first and second spot</li>
<li>/yaps &lt;d or define&gt; &lt;areaname&gt; - define area</li>
<li>/yaps &lt;d or define&gt; &lt;areaname&gt; &lt;owner&gt; - define area</li>
<li>/yaps &lt;i or info&gt; - show info about the area you are in</li>
<li>/yaps &lt;iw or infowand&gt; - get wand for area info</li>
<li>/yaps &lt;l or list&gt; - list all areas</li>
<li>/yaps &lt;dl or delete&gt; &lt;areaname&gt; - delete an already created area</li>
<li>/yaps &lt;tp or teleport&gt; &lt;areaname&gt; - teleport to an already created area</li>
<li>/yaps &lt;se or setexit&gt; &lt;areaname&gt; - set the exit point for all not allowed players</li>
</ul>
<p>obs: the area protected will be from bottom to top!</p>

<p><strong>By Block<br /> <br /></strong></p>
<ul>
<li>/yaps off - disable protected place block</li>
<li>/yaps on - enable protected place block</li>
<li>/yaps listp - list configured protected block types</li>
<li>/yaps listp &lt;block_id&gt; - check if that block type is configured as protected</li>
<li>/yaps &lt;bi or blockinfo&gt; - show info about the protected block you are looking at</li>
<li>/yaps &lt;bw or blockwand&gt; - get the protected block info wand</li>
</ul>

<p><strong>All</strong></p>
<ul>
<li>/yaps purge - purge old records</li>
<li><del>/yaps update - update plugin</del> Removed on 0.5</li>
<li>/yaps reload - reload plugin</li>
<li>/yaps &lt; help | ? | nothing &gt; - get help on commands</li>
</ul>

<h3 id="w-color-blue-permissions-color"><span style="color: #0000ff;">Permissions</span></h3>
<ul>
<li>yaps.admin ( All permission )
<ul>
<li>yaps.update ( For Update )</li>
<li>yaps.define ( For Define Area )</li>
<li>yaps.delete ( For Delete Area )</li>
<li>yaps.reload ( For reload )</li>
<li>yaps.wand ( For wand )</li>
<li>yaps.infowand ( For area info wand )</li>
<li>yaps.blockinfo ( For protected block info command and wand )</li>
<li>yaps.tp ( For tp to area )</li>
<li>yaps.info ( For info )</li>
<li>yaps.list ( For list )</li>
<li>yaps.setexit ( For set exit )</li>
<li>yaps.bypass ( For admin be able to block inside protection )</li>
<li>yaps.onoff ( For on / off protected blocks )</li>
<li>yaps.listprotectionblocks ( For see protected blocks list )</li>
<li>yaps.purge ( For old records )</li>
</ul>
</li>
</ul>

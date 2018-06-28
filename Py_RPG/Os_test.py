# -*- coding: utf-8 -*-
"""
Created on Fri Jun 22 11:42:18 2018

@author: jeremyh2
"""

import subprocess
import random
import ast

from monster.monster import blue_slime
from monster.monster import show_enemy_stat

from character.character_sheet import character
from character.character_sheet import character_create

class deadTarget(Exception):
    pass
        
#Subprocess calls
def subprocess_test():
    p = subprocess.check_output('dir', shell=True)

    print(p)

def encounter():
    encount_no = random.randint(1,4)
    battle=[]
    
    for i in range(encount_no):
        slime = blue_slime()
        battle.append(slime)
    
    print("")
    show_enemy_stat(battle)
   
    battle_list = len(battle)
    while(battle_list is not 0):
        while True:
            try:
                target = int(input("\nAttack which enemy? (Enter a number) "))
                
                if int(target) < 1:
                    raise ValueError
                
                target_s = battle[int(target) - 1]
                
                if target_s.is_alive() is False:
                    raise deadTarget("Invalid Target. Target already dead.")
                    
                break
            
            except (ValueError, IndexError) as e:
                print("Invalid Target.")
                show_enemy_stat(battle)
                continue
            
            except(deadTarget) as e:
                print(e.args[0])
                show_enemy_stat(battle)
                continue
            
        battle[int(target) - 1] = target_s.lose_hp(99) 
        targetted = battle[int(target)-1]
        
        if targetted.is_alive() is False:
            battle_list -= 1
        
        show_enemy_stat(battle)
        
    print("All Enemies Defeated.")
    
if __name__ == "__main__":
    print("Welcome!\n")
    player_character = None

    load = input("Would you like to load up a charcter? (Y for Yes and N for No) ")

    if load == 'Y':
        load = True
    else:
        load = False
    
    player_character = character_create(player_character, load)
    
    encounter()
    
    choice = input("\nWould you like to level up? (Y for Yes and N for No) ")
    while choice == 'Y':
        print("\nLEVEL UP!")
        
        player_character.level_up()
        choice = input("Press Y for another: ")
    
    player_character.save_char()
    
    input("Bye-bye!")
